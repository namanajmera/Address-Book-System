import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class AddressBook {

    static ArrayList<Contact> list = new ArrayList<Contact>();
    public static AddressBook addressBook = new AddressBook(null);
    public static Contact contact = new Contact();

    static Scanner sc = new Scanner(System.in);
    public static ArrayList<AddressBook> book = new ArrayList<>();
    public HashMap<String, String> citydict = new HashMap<>();
    public HashMap<String, String> statedict = new HashMap<>();
    public int count = 0;

    public AddressBook(String str) {

    }

    public static void defaultBook() {
        book.add(new AddressBook("Address Book 1"));
        book.add(new AddressBook("Address Book 2"));
        book.add(new AddressBook("Address Book 3"));
    }

    public void DefaultContact() {
        book.get(0).list.add(new Contact("Kalyani", "Bhimineni", "Lingayapalem", "Guntur", "Andhra", "522005",
                "9123456789", "bhiminenikalyani@gmail.com"));
        book.get(1).list.add(new Contact("Kalyani1", "Bhimineni1", "Lingayapalem", "Guntur", "Andhra", "522005",
                "9123456789", "bhiminenikalyani@gmail.com"));
        book.get(1).list.add(new Contact("Kalyani2", "Bhimineni2", "Lingayapalem", "Guntur", "Andhra", "522005",
                "9123456789", "kalyanibhimineni@gmail.com"));
        book.get(2).list.add(new Contact("Kalyani3", "Bhimineni3", "Lingayapalem", "Guntur", "Andhra", "522005",
                "9123456789", "kalyanibhimineni@gmail.com"));
        for (int i = 0; i < book.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void addAddressBook() {
        System.out.print("Enter name of new Address Book: ");
        String str = sc.next();
        book.add(new AddressBook(str));
    }

    public void Searchpersonwithcity() {
        System.out.println("Enter city for the contact info: ");
        String city = sc.next();
        for (int i = 0; i < list.size(); i++) {
            if (city.equals(list.get(i).getCity())) {
                System.out.println(list.get(i));
            }
        }
    }

    public void Searchpersonwithstate() {
        System.out.println("Enter State for the contact info: ");
        String city = sc.next();

        Optional<Contact> answer = list.stream().findAny();
        if (answer.equals(city)) {
            System.out.println(answer.get());
        } else {
            System.out.println("no values");
        }
    }

    public void PersonCityDictionary() {
        for (AddressBook address : book) {
            for (Contact contact : address.list) {
                String name = contact.getFirstName();
                citydict.put(name, contact.getCity());
            }
        }
        System.out.println("Enter the city name to search for contacts: ");
        String city = sc.next();
        for (Entry<String, String> entry : citydict.entrySet()) {
            if (city.equals(entry.getValue())) {
                System.out.println("Names form " + entry.getValue() + " city are: " + entry.getKey());
                count++;
            }
        }
        System.out.println("Count of contacts in " + city + " city is: " + count);
    }

    public void PersonStateDictionary() {
        for (AddressBook address : book) {
            for (Contact contact : address.list) {
                String name = contact.getFirstName();
                statedict.put(name, contact.getState());
            }
        }
        System.out.println("Enter the State name to search for contacts: ");
        String state = sc.next();
        for (Entry<String, String> entry : statedict.entrySet()) {
            if (state.equals(entry.getValue())) {
                System.out.println("Names form" + entry.getValue() + "State is: " + entry.getKey());
                count++;
            }
        }
        System.out.println("Count of contacts in " + state + " state is: " + count);
    }

    void AddDetails() {
        System.out.println("How many contats do you want to enter? ");
        int num = sc.nextInt();
        list.add(new Contact("Kalyani", "Bhimineni", "Lingayapalem", "Guntur", "Andhra", "522005", "9123456789",
                "bhiminenikalyani@gmail.com"));

        for (int i = 0; i < num; i++) {
            System.out.println("Enter FirstName");
            String firstName = sc.next();

            System.out.println("Enter LastName");
            String lastName = sc.next();

            System.out.println("Enter Address");
            String address = sc.next();

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
            if (!firstName.equals(list.get(0).getFirstName())) {
                list.add(new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email));
                System.out.println(list);
            } else {
                System.out.println("You have already entered this contact");
                break;
            }
            addressBook.toString();
        }
        addressBook.toString();
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

        AddressBook address = new AddressBook(null);

        System.out.print(
                "1.Add AddressBook \n2.Add Contact \n3.Delete \n4.Edit \n5.Search_Person_with_city \n6.Search_Person_with_State \n7.PersonCityDictionary+Count \n8.PersonStateDictionary+count");
        int check = sc.nextInt();
        switch (check) {
        case 1:
            addAddressBook();
            break;
        case 2:
            address.AddDetails();
            break;
        case 3:
            Delete();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
            break;
        case 4:
            Edit();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
            break;
        case 5:
            address.defaultBook();
            address.DefaultContact();
            address.Searchpersonwithcity();
            break;
        case 6:
            address.defaultBook();
            address.DefaultContact();
            address.Searchpersonwithstate();
            break;
        case 7:
            address.defaultBook();
            address.DefaultContact();
            address.PersonCityDictionary();
            break;
        case 8:
            address.defaultBook();
            address.DefaultContact();
            address.PersonStateDictionary();
            break;
        default:
            System.out.println("Invalid choice");
        }
    }
}
