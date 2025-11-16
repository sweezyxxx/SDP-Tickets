package com.example.tickets.gui;
import com.example.tickets.builder.EventBuilder;
import com.example.tickets.facade.ReservationSystemFacade;
import com.example.tickets.factory.Ticket;
import com.example.tickets.model.*;
import com.example.tickets.observer.NotificationCenter;
import com.example.tickets.strategy.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private ComboBox<Event> eventCombo;
    @FXML private ComboBox<String> strategyCombo;

    @FXML private CheckBox baggageCheck;
    @FXML private CheckBox insuranceCheck;

    @FXML private Button bookButton;
    @FXML private Button showSeatsButton;

    @FXML private TextArea resultArea;
    @FXML private ListView<String> notificationList;

    private final NotificationCenter center = new NotificationCenter();
    private final ReservationSystemFacade facade = new ReservationSystemFacade(center);

    private final List<Event> allEvents = new ArrayList<>();

    private final Event concert =
            new EventBuilder("Concert", EventType.Concert)
                    .addRow('A', 3, 40000)  // дорогой ряд
                    .addRow('B', 3, 25000)
                    .addRow('C', 3, 15000)  // самый дешёвый ряд
                    .build();

    private final Event plane =
            new EventBuilder("Plane", EventType.Plane)
                    .addRow('A', 3, 55000)
                    .addRow('B', 3, 45000)
                    .addRow('C', 3, 30000)
                    .build();

    private final Event cinema =
            new EventBuilder("Cinema", EventType.Cinema)
                    .addRow('A', 3, 7000)   // ближе к экрану = дороже
                    .addRow('B', 3, 5500)
                    .addRow('C', 3, 10000)  // VIP диваны
                    .build();

    @FXML
    public void initialize() {

        allEvents.add(concert);
        allEvents.add(plane);
        allEvents.add(cinema);

        eventCombo.getItems().addAll(allEvents);
        eventCombo.getSelectionModel().select(0);

        strategyCombo.getItems().addAll("Cheapest First", "Premium First");
        strategyCombo.getSelectionModel().select(0);

        center.add(notificationList.getItems()::add);

        bookButton.setOnAction(e -> book());
        showSeatsButton.setOnAction(e -> openFreeSeatsWindow(eventCombo.getValue()));
    }


    private SeatingStrategy getStrategy() {
        return switch (strategyCombo.getValue()) {
            case "Premium First" -> new PremiumFirstStrategy();
            default -> new CheapestFirstStrategy();
        };
    }

    private void book() {
        resultArea.clear();

        Event event = eventCombo.getValue();
        User user = new User("Temirlan");

        Ticket ticket = facade.bookTicket(
                user,
                event,
                getStrategy(),
                baggageCheck.isSelected(),
                insuranceCheck.isSelected()
        );

        if (ticket == null) {
            resultArea.setText("No available seats for this event.");
            return;
        }

        resultArea.setText(
                ticket.getDescription() + "\nPrice: " + ticket.getPrice()
        );
    }

    private void openFreeSeatsWindow(Event event) {

        Stage stage = new Stage();
        stage.setTitle("Free Seats");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        VBox.setVgrow(area, Priority.ALWAYS);

        List<Seat> free = event.getSeats().stream()
                .filter(s -> !s.isTaken())
                .sorted((a, b) -> Double.compare(b.getBasePrice(), a.getBasePrice()))
                .toList();

        StringBuilder sb = new StringBuilder();

        if (free.isEmpty()) {
            sb.append("No free seats.");
        } else {
            sb.append("Free seats:\n\n");

            for (Seat seat : free) {
                sb.append(seat.getId())
                        .append("   →  ")
                        .append(seat.getBasePrice())
                        .append(" ₸")
                        .append("\n");
            }
        }

        area.setText(sb.toString());
        root.getChildren().add(area);

        Scene scene = new Scene(root, 200, 250);
        stage.setScene(scene);
        stage.show();
    }

}
