import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    // Variables to store ticket information
    private int row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to create a Ticket object
    public Ticket(int row, int seat, double price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

        // Save ticket information to a file when creating the ticket
        saveToFile();
    }

    // Method to save ticket information to a file
    public void saveToFile() {

        // Convert row number to character
        char rowChar = (char) ('A' + row);

        // Create the file name using the row label and seat number
        String fileName = rowChar + String.valueOf(seat + 1) + ".txt";



        try (FileWriter writer = new FileWriter(fileName)) {
            // Write ticket information to the file
            writer.write("Ticket Information\n");
            writer.write("Row: " + (char) ('A' + row) + "\n");
            writer.write("Seat: " + (seat + 1) + "\n");
            writer.write("Price: " + price + "\n");
            writer.write("Person Information:\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");

            // Print a message to inform that ticket information is saved to text file
            System.out.println("Ticket information saved to " + fileName);

        } catch (IOException e) {
            // Handle errors that occur while saving ticket information
            System.out.println("Error occurred while saving ticket information to " + fileName);
            e.printStackTrace();

        }
    }

    // Getter method to retrieve the row of the ticket
    public int getRow(){
        return row;
    }

    // Setter method to set the row of the ticket
    public void setRow(int row) {
        this.row = row;
    }

    // Getter method to retrieve the seat number of a ticket
    public int getSeat(){
        return seat;
    }

    // Setter method to set the seat number of the ticket
    public void setSeat(int seat){
        this.seat = seat;
    }

    // Getter method to retrieve the price of the ticket
    public double getPrice(){
        return price;
    }

    // Setter method to set the price of the ticket
    public void setPrice(double price){
        this.price= price;
    }

    // Getter method to retrieve the person associated with the ticket
    public Person getPerson() {
        return person;
    }

    // Setter method to set the person associated with the ticket
    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket details
    public void printTicketDetails() {
        System.out.println("Row: " + row);
        System.out.println("Seat: "+ seat);
        System.out.println("Price: "+ price);
        System.out.println("Person Information:");
        person.printDetails();
    }
}
