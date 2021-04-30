import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

    private static int bookNumber = 0;
    private static String firstName;
    private static String lastName;
    private static String area;
    private static String city;
    private static String state;
    private static int pin;
    private static int phoneNumber;
    private static String email;

    public static Scanner sc = new Scanner(System.in);

    public static ArrayList<AddressBookDetails> addressBook = new ArrayList<>();

    public static void addAdressBookDetails() {
        System.out.println("Enter Name of Address Book");
        String name = sc.next();
        addressBook.add(new AddressBookDetails(name));
    }

    public static void pickAddressBook() {
        System.out.println("You are Currently in " + addressBook.get(bookNumber) + " AddressBook");
        if (addressBook.size() > 1) {
            for (int i = 0; i < addressBook.size(); i++)
                System.out.println(i + ". " + addressBook.get(i));
            System.out.println("Pick Address Book Number");
            bookNumber = Integer.parseInt(sc.next());
        }
    }

    public static void personByState() {
        System.out.println("Enter State Name");
        state = sc.next();
        for (int i = 0; i < addressBook.size(); i++)
            for (int j = 0; j < addressBook.get(i).list.size(); j++)
                if (addressBook.get(i).list.get(j).getState().equals(state))
                    System.out.println(addressBook.get(i).list.get(j));
    }

    public static void personByCity() {
        System.out.println("Enter City Name");
        city = sc.next();
        for (int i = 0; i < addressBook.size(); i++)
            for (int j = 0; j < addressBook.get(i).list.size(); j++)
                if (addressBook.get(i).list.get(j).getCity().equals(city))
                    System.out.println(addressBook.get(i).list.get(j));
    }

    public static void option() {
        Scanner sc = new Scanner(System.in);
        String check = "Y";
        while ((check.equals("Y")) || (check.equals("y"))) {
            System.out.println("Choose Below Option");
            System.out.println("1: Add Contact");
            System.out.println("2: Edit Contact");
            System.out.println("3: Delete Contact");
            System.out.println("4: Display Contact");
            System.out.println("5: Exit");
            String choose = sc.next();
            switch (choose) {
            case "1":
                addressBook.get(bookNumber).addDetails();
                break;
            case "2":
                pickAddressBook();
                System.out.println(addressBook.get(bookNumber).editDetails());
                break;
            case "3":
                pickAddressBook();
                System.out.println(addressBook.get(bookNumber).deleteDetails());
                break;
            case "4":
                pickAddressBook();
                addressBook.get(bookNumber).displayDetails();
                break;
            default:
                System.out.println("Exit");
                System.out.println("Want to Make More Changes in This Address Book? (y/n)");
                check = sc.next();
            }
        }
    }

    public static void search() {
        System.out.println("Choose Option");
        System.out.println("1: By City Name");
        System.out.println("2: By State Name");

        String choose = sc.next();
        switch (choose) {

        case "1":
            personByCity();
            break;
        case "2":
            personByState();
            break;
        default:
            System.out.println("Wrong Input");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        String check = "Y";
        while ((check.equals("Y")) || (check.equals("y"))) {
            addAdressBookDetails();
            option();
            System.out.println("Do You Want to Search Contacts By Certain Details Like by City, State?");
            System.out.println("Press y if You Want to Search");
            String num = sc.next();
            if (num.equals("Y") || num.equals("y")) {
                search();
            } else {
                System.out.println("You Can Proceed forward");
            }
            System.out.println("Want to Add More Address Book (y/n)");
            check = sc.next();
        }
    }
}
