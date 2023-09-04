package org.example;

/*
Creare una classe Biglietteria, che contiene il metodo main in cui:
● chiedere all’utente di inserire il numero di km e l’età del passeggero
● con quei dati provare a creare un nuovo Biglietto (gestire eventuali eccezioni con try-catch)
● stampare a video il prezzo del biglietto calcolato
 */

import java.util.Scanner;

public class TicketOffice {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);


        boolean run = true;
        while (run){
            System.out.print("Enter km: ");
            int km = Integer.parseInt(scan.nextLine());
            System.out.print("Enter age: ");
            int age = Integer.parseInt(scan.nextLine());

            try{
                Ticket ticket = new Ticket(km, age);
                System.out.println("Your ticket: " + ticket + "€ " + ticket.getPrice());
                run = false;
            } catch (RuntimeException e){
                System.out.println("Invalid input.");
            }

        }
    }
}