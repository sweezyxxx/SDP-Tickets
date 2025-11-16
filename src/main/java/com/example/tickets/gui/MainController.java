package com.example.tickets.gui;

import com.example.tickets.facade.ReservationSystemFacade;
import com.example.tickets.factory.Ticket;
import com.example.tickets.model.*;
import com.example.tickets.observer.NotificationCenter;
import com.example.tickets.strategy.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;



public class MainController {

    @FXML private ComboBox<EventType> eventTypeCombo;
    @FXML private ComboBox<String> strategyCombo;

    @FXML private CheckBox baggageCheck;
    @FXML private CheckBox insuranceCheck;

    @FXML private Button bookButton;

    @FXML private TextArea resultArea;
    @FXML private ListView<String> notificationList;
    @FXML private Button showSeatsButton;
    @FXML private Button loadExternalButton;

    private final NotificationCenter center = new NotificationCenter();
    private final ReservationSystemFacade facade = new ReservationSystemFacade(center);

    private final Event concert = new Event("Imagine Dragons", EventType.CONCERT);
    private final Event plane = new Event("Air Astana Flight", EventType.PLANE);
    private final Event cinema = new Event("Dune 2", EventType.CINEMA);

    @FXML
    public void initialize() {

        showSeatsButton.setOnAction(e -> showFreeSeats());
        loadExternalButton.setOnAction(e -> loadExternal());

        for (char row = 'A'; row <= 'C'; row++) {
            for (int col = 1; col <= 3; col++) {

                SeatType type;
                double price;

                switch (row) {
                    case 'A' -> {         // VIP
                        type = SeatType.VIP;
                        price = 50000;
                    }
                    case 'B' -> {         // Best View
                        type = SeatType.BEST_VIEW;
                        price = 35000;
                    }
                    default -> {          // Regular
                        type = SeatType.REGULAR;
                        price = 20000;
                    }
                }

                concert.addSeat(new Seat("C" + row + col, price, type));
            }
        }




        for (int row = 1; row <= 3; row++) {
            for (char col = 'A'; col <= 'C'; col++) {

                SeatType type;
                double price;

                switch (row) {
                    case 1 -> {           // VIP
                        type = SeatType.VIP;
                        price = 70000;
                    }
                    case 2 -> {           // Best View
                        type = SeatType.BEST_VIEW;
                        price = 55000;
                    }
                    default -> {          // Regular
                        type = SeatType.REGULAR;
                        price = 40000;
                    }
                }

                plane.addSeat(new Seat(row + "" + col, price, type));
            }
        }




        for (char row = 'A'; row <= 'C'; row++) {
            for (int col = 1; col <= 3; col++) {

                SeatType type;
                double price;

                switch (row) {
                    case 'A' -> {
                        type = SeatType.BEST_VIEW;
                        price = 6500;
                    }
                    case 'B' -> {
                        type = SeatType.REGULAR;
                        price = 5000;
                    }
                    default -> {
                        type = SeatType.VIP;
                        price = 9000;
                    }
                }

                cinema.addSeat(new Seat(row + "" + col, price, type));
            }
        }



        // GUI setup
        eventTypeCombo.getItems().addAll(EventType.values());
        eventTypeCombo.getSelectionModel().select(EventType.CONCERT);

        strategyCombo.getItems().addAll("Cheapest", "Best View", "VIP First");
        strategyCombo.getSelectionModel().select(0);

        center.add(notificationList.getItems()::add);

        bookButton.setOnAction(e -> book());
    }


    private Event getEvent(EventType type) {
        return switch (type) {
            case CONCERT -> concert;
            case PLANE -> plane;
            default -> cinema;
        };
    }

    private SeatingStrategy getStrategy() {
        return switch (strategyCombo.getValue()) {
            case "Best View" -> new BestViewStrategy();
            case "VIP First" -> new VipFirstStrategy();
            default -> new CheapestSeatStrategy();
        };
    }

    private void book() {
        resultArea.clear();

        User user = new User("Client1");

        Event event = getEvent(eventTypeCombo.getValue());
        SeatingStrategy strategy = getStrategy();

        Ticket ticket = facade.bookTicket(
                user,
                event,
                strategy,
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

    private void showFreeSeats() {
            Event event = getEvent(eventTypeCombo.getValue());
            openFreeSeatsWindow(event);
    }

    private void openFreeSeatsWindow(Event event) {
        // создаём новое окно
        Stage stage = new Stage();
        stage.setTitle("Free Seats");

        // главный контейнер
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // текстовое поле для отображения мест
        TextArea area = new TextArea();
        area.setEditable(false);

        // категории
        List<Seat> vip = event.getFreeSeatsByType(SeatType.VIP);
        List<Seat> best = event.getFreeSeatsByType(SeatType.BEST_VIEW);
        List<Seat> regular = event.getFreeSeatsByType(SeatType.REGULAR);

        StringBuilder sb = new StringBuilder();

        if (vip.isEmpty() && best.isEmpty() && regular.isEmpty()) {
            sb.append("No free seats.");
        } else {

            if (!vip.isEmpty()) {
                sb.append("VIP:\n");
                vip.forEach(s -> sb.append("  ").append(s.getId())
                        .append(" (").append(s.getBasePrice()).append("₸)\n"));
                sb.append("\n");
            }

            if (!best.isEmpty()) {
                sb.append("Best View:\n");
                best.forEach(s -> sb.append("  ").append(s.getId())
                        .append(" (").append(s.getBasePrice()).append("₸)\n"));
                sb.append("\n");
            }

            if (!regular.isEmpty()) {
                sb.append("Regular:\n");
                regular.forEach(s -> sb.append("  ").append(s.getId())
                        .append(" (").append(s.getBasePrice()).append("₸)\n"));
            }
        }

        area.setText(sb.toString());

        // добавляем в окно
        root.getChildren().add(area);

        // показываем
        stage.setScene(new Scene(root, 350, 400));
        stage.show();
    }
    private void loadExternal() {
        List<Event> flights = facade.loadExternalEvents();

        StringBuilder sb = new StringBuilder("External flights:\n");

        for (Event e : flights) {
            sb.append(e.getName())
                    .append(" [").append(e.getType()).append("]\n");
        }

        resultArea.setText(sb.toString());
    }

}
