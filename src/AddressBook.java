import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class AddressBook {
    static ArrayList<ContactPerson> list = new ArrayList<ContactPerson>();
    public static AddressBook addressBook = new AddressBook();
    public static HashMap<String, AddressBook> addressBooks = new HashMap<>();

    static Scanner sc = new Scanner(System.in);
    public static String check = "Y";

    public static void AddAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of new address book");
        String name = sc.nextLine();
        addressBook = new AddressBook();
        addressBooks.put(name, addressBook);
        System.out.println("New addressbook added ");

    }

    private void AddDetails() {
        System.out.println("How many contats do you want to enter? ");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter FirstName");
            String firstName = sc.next();

            System.out.println("Enter LastName");
            String lastName = sc.next();

            System.out.println("Enter Address");
            String area = sc.next();

            System.out.println("Enter CityName");
            String city = sc.next();

            System.out.println("Enter StateName");
            String state = sc.next();

            System.out.println("Enter ZipCode");
            String zip = sc.next();

            System.out.println("Enter PhoneNumber");
            String phoneNumber = sc.next();

            System.out.println("Enter Email");
            String email = sc.next();

            list.add(new ContactPerson(firstName, lastName, area, city, state, zip, phoneNumber, email));
            System.out.println(list);
        }
    }

    public static String Edit() {
        String name;
        System.out.println("Enter First Name of Details to be Edited: ");
        name = sc.next();

        if (name.equals(list.get(0).getFirstName())) {
            System.out.println("Enter FirstName");
            list.get(0).setFirstName(sc.next());

            System.out.println("Enter LastName");
            list.get(0).setLastName(sc.next());

            System.out.println("Enter Address");
            list.get(0).setAddress(sc.next());

            System.out.println("Enter CityName");
            list.get(0).setCity(sc.next());

            System.out.println("Enter StateName");
            list.get(0).setState(sc.next());

            System.out.println("Enter ZipCode");
            list.get(0).setZip(sc.next());

            System.out.println("Enter PhoneNumber");
            list.get(0).setPhoneNumber(sc.next());

            System.out.println("Enter Email");
            list.get(0).setEmail(sc.next());

            System.out.println(list.get(0));
            return "Contact Edited";
        } else {
            return "Name Not Available in List";
        }
    }

    public static String Delete() {
        String name;
        System.out.print("Enter FirstName");
        name = sc.next();

        if (name.equals(list.get(0).getFirstName())) {
            list.remove(0);
            return "Deleted";
        } else {
            return "Name Not Available in List";
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome To Address Book Problem\n");

        AddressBook address = new AddressBook();
        AddAddressBook();
        address.AddDetails();

        System.out.print("Do you want to Edit this Contact ? (y/n)");
        check = sc.next();
        if (check.equalsIgnoreCase("y")) {
            System.out.println(Edit());
        } else {
            System.out.println("Done");
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.print("Do you want to Delete this Contact ? (y/n)");
        check = sc.next();
        if (check.equalsIgnoreCase("y")) {
            System.out.println(Delete());
        } else {
            System.out.println("Done");
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
