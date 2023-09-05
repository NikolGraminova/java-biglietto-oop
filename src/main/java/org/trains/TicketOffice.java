package org.trains;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class TicketOffice {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/trains";
        String user = "root";
        String password = "Myroot23?";

        String sql1 = "select * from routes";
        String sql2 = "select * from routes where id = ?";


        // display routes
        System.out.println("Welcome to train ticket selection, available routes:");
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            try(PreparedStatement ps = conn.prepareStatement(sql1)){
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
                        PreparedStatement preparedStatement = conn.prepareStatement(sql2);
                        try (PreparedStatement ps = conn.prepareStatement(sql2)) {
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
                                    boolean isFlexible = Boolean.parseBoolean(scan.nextLine());

                                    try {
                                        Ticket ticket = new Ticket(km, age, date, isFlexible);
                                        System.out.println("Your ticket: " + ticket);
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
        }
        scan.close();
    }
}
