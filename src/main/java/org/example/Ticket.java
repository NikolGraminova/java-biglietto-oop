package org.example;

/*
Consegna: creare una classe Biglietto con due attributi interi: km e età del passeggero.
Nel costruttore vanno valorizzati entrambi gli attributi, assicurandosi che abbiano valori validi
(creare due metodi isValidKm e isValidEta che implementano questa logica).
In caso anche solo uno non sia valido, sollevare un’eccezione. Aggiungere tre costanti:
● il costo chilometrico di 21 centesimi di €/km (BigDecimal),
● lo sconto over 65 del 40 % (BigDecimal)
● lo sconto minorenni del 20% (BigDecimal).
Implementare il metodo public che calcola il prezzo del biglietto comprensivo di eventuale sconto (calcolaPrezzo).
Per eseguire il calcolo dello sconto aggiungere un metodo private calcolaSconto, da chiamare dentro al metodo calcolaPrezzo.
 */


import java.math.BigDecimal;

public class Ticket {

    // fields
    private int km;
    private int age;
    private final BigDecimal pricePerKm = new BigDecimal("0.21");
    private final BigDecimal discountOver65 = new BigDecimal("0.40");
    private final BigDecimal discountUnder18 = new BigDecimal("0.20");


    // constructors
    public Ticket(int km, int age) {
        isValidKm(km);
        this.km = km;

        isValidAge(age);
        this.age = age;
    }


    // getters and setters
    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        isValidKm(km);
        this.km = km;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        isValidAge(age);
        this.age = age;
    }


    // methods
    public BigDecimal getPrice(){
        return pricePerKm.subtract(getDiscount());
    }

    public BigDecimal getDiscount(){
        if (age < 18){
            return discountUnder18;
        } else if (age > 65){
            return discountOver65;
        } return BigDecimal.valueOf(0);
    }





    private void isValidKm (int km) throws RuntimeException{
        if (km < 0 ){
            throw new RuntimeException();
        }
    }

    private void isValidAge(int age) throws RuntimeException{
        if (age > 100 ||age < 0){
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return  "kilometers: " + km +
                "; age: " + age +
                "; ";
    }
}
