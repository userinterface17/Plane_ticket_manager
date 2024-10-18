import java.util.InputMismatchException;
import java.util.Scanner;

//class for managing plane seat booking
public class PlaneManagement {
    //Constants to define number of rows and seats per row
    static final int ROWS = 4;
    static final int[] SEATS = {14, 12, 12, 14};
    //Array to represent characters of row labels
    static final char[] ROW_LABELS = {'A', 'B', 'C', 'D'};
    // 2D array for seat availability
    static final int[][] seatsAvailability = new int[ROWS][14];
    //Array to store tickets sold
    static Ticket[] ticketsSold = new Ticket[ROWS * 14];
    //Scanner object for user input
    static final Scanner input = new Scanner(System.in);


    // main method
    public static void main(String[] args) {

        //for initial seat availability
        initializeSeats();

        //welcome message
        System.out.println("Welcome to the Plane Management application");

        Scanner input = new Scanner(System.in);
        int option;

        do {
            try {
                displayMenu();
                option = input.nextInt();
                input.nextLine();

                //switch case for menu options
                switch (option) {

                    case 1:
                        //buy a seat
                        buySeat();
                        break;

                    case 2:
                        //cancel a seat
                        cancelSeat();
                        break;

                    case 3:
                        //find the first available seat
                        findFirstAvailable();
                        break;

                    case 4:
                        //show seating plan
                        showSeatingPlan();
                        break;

                    case 5:
                        //print tickets information
                        printTicketsInfo();
                        break;

                    case 6:
                        // search for a ticket
                        searchTicket();
                        break;

                    case 0:
                        //exit the program
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // Consume invalid input to avoid infinite loop
                option = -1; // Set option to an invalid value to prompt the menu again
            }


        } while (option != 0);
    }


    //method to initialize seat availability
    public static void initializeSeats() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS[i]; j++) {
                seatsAvailability[i][j] = 0;
            }
        }
    }


    //method to display menu options
    public static void displayMenu() {
        System.out.println("************************************************");
        System.out.println("*                MENU OPTIONS                  *");
        System.out.println("************************************************");
        System.out.println("            1. Buy a seat"                       );
        System.out.println("            2. Cancel a seat"                    );
        System.out.println("            3. Find the first available seat"    );
        System.out.println("            4. Show seating plan"                );
        System.out.println("            5. Print tickets information"        );
        System.out.println("            6. Search ticket"                    );
        System.out.println("            0. Exit                             ");
        System.out.println("************************************************");
        System.out.print("Please enter your option: ");
    }


    //method to buy a seat
    public static void buySeat() {
        //prompt user to enter row letter
        System.out.print("Enter row letter (A-D): ");
        char rowChar = input.next().toUpperCase().charAt(0);
        int row = rowChar - 'A';

        if (row >= 0 && row < ROWS) {
            //prompt user to enter seat number
            System.out.print("Enter seat number (1-" + SEATS[row] + "): ");
            int seat = input.nextInt() - 1;

            if (seat >= 0 && seat < SEATS[row]) {
                //prompt user to enter personal information
                if (seatsAvailability[row][seat] == 0) {
                    System.out.print("Enter person's name: ");
                    String name = input.next();

                    System.out.print("Enter person's surname: ");
                    String surname = input.next();

                    System.out.print("Enter person's email: ");
                    String email = input.next();

                    //create Person object
                    Person person = new Person(name, surname, email);

                    //object to calculate price based on row and seat number
                    double price = getPriceForRowAndSeat(row, seat);
                    //create ticket object
                    Ticket ticket = new Ticket(row, seat, price, person);
                    //store ticket in ticketSold array
                    ticketsSold[row * SEATS[row] + seat] = ticket;

                    //mark seat as occupied
                    seatsAvailability[row][seat] = 1;

                    System.out.println("Seat purchased successfully");

                } else {
                    System.out.println("Seat is already occupied.");
                }
            } else {
                System.out.println("Invalid seat number. Please enter a seat number between 1 and " + SEATS[row]);
            }
        } else {
            System.out.println("Invalid row letter. Please enter a row letter between A and D.");
        }
    }

    //method to cancel a seat
    public static void cancelSeat() {
        System.out.print("Enter row letter (A-D): ");
        char rowChar = input.next().toUpperCase().charAt(0);
        int row = rowChar - 'A';

        if (row >= 0 && row < ROWS) {
            System.out.print("Enter seat number (1-" + SEATS[row] + "): ");
            int seat = input.nextInt() - 1;

            if (seat >= 0 && seat < SEATS[row]) {
                if (seatsAvailability[row][seat] == 1) {
                    seatsAvailability[row][seat] = 0;
                    System.out.println("Seat canceled successfully");
                } else {
                    System.out.println("Seat is not occupied.");
                }
            } else {
                System.out.println("Invalid seat number.");
            }
        } else {
            System.out.println("Invalid row letter.");
        }
    }


    //method to find the first available seat
    public static void findFirstAvailable() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS[i]; j++) {
                if (seatsAvailability[i][j] == 0) {
                    System.out.println("First available seat is at Row " + ROW_LABELS[i] + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    //method to show the seat plan
    public static void showSeatingPlan() {
        System.out.println("Seating Plan:");
        for (int i = 0; i < ROWS; i++) {
            System.out.print("Row " + ROW_LABELS[i] + ": ");
            for (int j = 0; j < SEATS[i]; j++) {
                if (seatsAvailability[i][j] == 0) {
                    System.out.print("O ");// O means available
                } else {
                    System.out.print("X ");// X means occupied
                }
            }
            System.out.println();
        }
    }


    //method to check tickets sold
    public static void searchTicket() {
        System.out.print("Enter row letter (A-D): ");
        char rowChar = input.next().toUpperCase().charAt(0);
        int row = rowChar - 'A';

        if (row >= 0 && row < ROWS) {
            System.out.print("Enter seat number (1-" + SEATS[row] + "): ");
            int seat = input.nextInt() - 1;

            if (seat >= 0 && seat < SEATS[row]) {
                if (seatsAvailability[row][seat] == 1) {
                    Ticket ticket = ticketsSold[row * SEATS[row] + seat];
                    System.out.println("Ticket and Person information:");
                    ticket.printTicketDetails();
                } else {
                    System.out.println("This seat is available.");
                }
            } else {
                System.out.println("Invalid seat number.");
            }
        } else {
            System.out.println("Invalid row letter.");
        }
    }

    //
    public static void printTicketsInfo() {
        int totalPrice = 0;
        System.out.println("Tickets Information:");

        for (Ticket ticket : ticketsSold) {
            if (ticket != null) {
                System.out.println(ticket);
                totalPrice += ticket.getPrice();
            }
        }

        System.out.println("Total price of tickets sold during the session: £" + totalPrice);
    }

    //methods to get prices for tickets
    public static double getPriceForRowAndSeat(int row, int seat) {

        // for seats from A1 to D5
        if ((row >= 0 && row <= 3) && (seat >= 0 && seat <= 4)) {
            return 200.0;

        // for seats from A6 to D9
        } else if ((row >= 0 && row <= 3) && (seat >= 5 && seat <= 8)) {
            return 150.0;

        // for rest of the seats
        } else {
            return 180.0;
        }
    }
}

