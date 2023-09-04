package org.trains;

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
BONUS:
Alla classe Biglietto aggiungere i seguenti attributi:
● data: LocalDate
● flessibile: boolean
Entrambi gli attributi vanno valorizzati nel costruttore.
Aggiungere due costanti:
● durata normale: 30 giorni (int)
● durata flessibile: 90 giorni (int)
Aggiungere un metodo (calcolaDataScadenza: LocalDate) che calcola la data di scadenza del biglietto, applicando la durata normale o flessibile in base al parametro flessibile(boolean).
Nel metodo che calcola il prezzo, se il biglietto è flessibile, maggiorare il prezzo del 10%.
Modificare la classe Biglietteria in modo che, alla creazione del Biglietto, valorizzi la data con la data odierna e il parametro flessibile in base alla scelta dell’utente.
Dopo aver stampato il prezzo del biglietto, stampare a video anche la data di scadenza.
 */


import java.math.BigDecimal;
import java.time.LocalDate;

public class Ticket {

    // fields
    private int km;
    private int age;
    private LocalDate date;
    private boolean isFlexible;
    private final BigDecimal pricePerKm = new BigDecimal("0.21");
    private final BigDecimal discountOver65 = new BigDecimal("0.40");
    private final BigDecimal discountUnder18 = new BigDecimal("0.20");
    private final BigDecimal flexiblePrice = new BigDecimal("0.10");
    private final int normalDuration = 30;
    private final int flexibleDuration = 90;


    // constructors
    public Ticket(int km, int age, LocalDate date, boolean isFlexible) {
        isValidKm(km);
        this.km = km;
        isValidAge(age);
        this.age = age;
        this.date = date;
        this.isFlexible = isFlexible;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isFlexible() {
        return isFlexible;
    }

    public void setFlexible(boolean flexible) {
        isFlexible = flexible;
    }


    // methods
    public BigDecimal getPrice(){
        BigDecimal price = pricePerKm.multiply(BigDecimal.valueOf(km));
        BigDecimal discount = price.multiply(getDiscount());
        BigDecimal finalPrice = price.subtract(discount);
        if (isFlexible){
            BigDecimal percentage = price.multiply(flexiblePrice);
            return finalPrice.add(percentage);
        } else{
            return finalPrice;
        }
    }

    public BigDecimal getDiscount(){
        if (age < 18){
            return discountUnder18;
        } else if (age > 65){
            return discountOver65;
        } return BigDecimal.valueOf(0);
    }

    public LocalDate getExpirationDate(){
        if (isFlexible){
            return date.plusDays(flexibleDuration);
        } else return date.plusDays(normalDuration);
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
                "; age: " + age + "; " +
                "flexible: " + isFlexible + "; " +
                "expiration date: " + getExpirationDate() + "; " +
                "price: ";
    }
}
