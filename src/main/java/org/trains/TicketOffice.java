package org.trains;

/*
Creare una classe Biglietteria, che contiene il metodo main in cui:
● chiedere all’utente di inserire il numero di km e l’età del passeggero
● con quei dati provare a creare un nuovo Biglietto (gestire eventuali eccezioni con try-catch)
● stampare a video il prezzo del biglietto calcolato
BONUS:
Creare una database “db_treni” e aggiungere una tabella “tratte”, che comprende le seguenti colonne:
● id
● partenza
● arrivo
● km
Inserire nella tabella un insieme di dati, che rappresentano le tratte ferroviarie (ad es. partenza: Milano, arrivo: Roma, km: 574)
Modificare la classe Biglietteria per fare in modo di connettersi al database e leggere l’elenco delle tratte ferroviarie dalla tabella.
Invece di inserire manualmente il numero di km per creare un biglietto, permettere all’utente di scegliere una delle tratte e leggere il numero di km corrispondente.
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class TicketOffice {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/trains";
        String user = "root";
        String password = "Myroot23?";

        // display routes
        System.out.println("Welcome to train ticket selection, available routes:");
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            String sql = "select * from routes";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String id = rs.getString("id");
                        String arrival = rs.getString("arrival");
                        String depart = rs.getString("depart");
                        String kilometers = rs.getString("kilometers");
                        System.out.println(id + ". " + arrival + "-" + depart + " kilometers: " + kilometers);
                    }
                }
            }
        } catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }

        // interactive menu
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        while (run){
            System.out.print("Generate ticket? (Y/N): ");
            String choice = scan.nextLine();
            switch (choice.toLowerCase()) {
                case "y" -> {
                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        System.out.print("Choose a route: ");
                        int routeChoice = Integer.parseInt(scan.nextLine());
                        String sql = "select * from routes where id = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setInt(1, routeChoice);
                            try (ResultSet rs = ps.executeQuery()) {
                                while (rs.next()) {
                                    String id = rs.getString("id");
                                    String arrival = rs.getString("arrival");
                                    String depart = rs.getString("depart");
                                    String kilometers = rs.getString("kilometers");
                                    System.out.println(id + ". " + arrival + "-" + depart + " kilometers: " + kilometers);

                                    int km = Integer.parseInt(kilometers);
                                    System.out.print("Enter your age: ");
                                    int age = Integer.parseInt(scan.nextLine());
                                    LocalDate date = LocalDate.now();
                                    System.out.println("Do you want a flexible duration ticket? (TRUE/FALSE)");
                                    boolean isFlexible = scan.nextBoolean();

                                    try {
                                        Ticket ticket = new Ticket(km, age, date, isFlexible);
                                        System.out.println("Your ticket: " + ticket + "€" + String.format("%.2f", ticket.getPrice()) + ";");
                                    } catch (RuntimeException e) {
                                        System.out.println("Invalid input.");
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("An error occurred");
                        e.printStackTrace();
                    }
                }
                case "n" -> {
                    System.out.println("Closing program.");
                    run = false;
                }
                default -> System.out.println("Invalid choice.");
            }
            scan.nextLine();
        }
        scan.close();
    }
}
