import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddressBook {
    public static final String ADRESS_BOOK_FILES = "E:\\Ebook\\BridgeLabz\\Assignment\\Address Book System\\Files";
    private List<Contacts> addressBook;
    private HashSet<String> contactsByCity;
    private HashSet<String> contactsByState;
    private ArrayList<String> city;
    private ArrayList<String> state;


    public enum IOservice {
        CONSOLE_IO, FILE_IO
    }

    public AddressBook() {
        this.addressBook = new ArrayList<Contacts>();
        this.contactsByCity = new HashSet<String>();
        this.contactsByState = new HashSet<String>();
        this.city = new ArrayList<String>();
        this.state = new ArrayList<String>();
    }

    public AddressBook(List<Contacts> addressBook) {
        this.addressBook = addressBook;
    }

    public void addContactToAddressBook(Contacts contact) {
        Predicate<Contacts> isPresent = n -> {
            return n.equals(contact);
        };
        if (!addressBook.stream().anyMatch(isPresent))
            addressBook.add(contact);
        else
            System.out.println("Duplicate Contact! Enter a new Contact or Edit exixting Contact");
    }

    public void displayAddressBook() {
        addressBook.stream().forEach(n -> System.out.println(n.toString()));

    }

    public void editContact(String fullName) {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        for (Contacts contact : addressBook) {
            if (fullName.toUpperCase().equals((contact.getFullName()).toUpperCase())) {
                System.out.println("Choose What to EDIT : ");
                System.out.println("1. Address");
                System.out.println("2. City");
                System.out.println("3. State");
                System.out.println("4. Zip");
                System.out.println("5. Phone Number");
                System.out.println("6. Email");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter new address:");
                        sc.nextLine();
                        contact.setAddress(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter new city:");
                        contact.setCity(sc.next());
                        break;
                    case 3:
                        System.out.println("Enter new state:");
                        sc.nextLine();
                        contact.setState(sc.nextLine());
                        break;
                    case 4:
                        System.out.println("Enter new zip:");
                        contact.setZip(sc.nextInt());
                        break;
                    case 5:
                        System.out.println("Enter new phone number:");
                        contact.setPhoneNumber(sc.next());
                        break;
                    case 6:
                        System.out.println("Enter new email:");
                        contact.setEmail(sc.next());
                        break;
                    default:
                        System.out.println("INVALID choice");
                }
                flag = false;
                break;
            }
        }
        if (flag)
            System.out.println("Contact doesn't Exist");

    }

    public void deleteContact(String name) {
        boolean flag = true;
        for (Contacts contact : addressBook) {
            if (name.toUpperCase().equals((contact.getFullName()).toUpperCase())) {
                addressBook.remove(contact);
                flag = false;
            }
        }
        if (flag)
            System.out.println("Contact doesn't Exist");
        else
            System.out.println("Contact Deleted");
    }

    public Contacts createContact() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter First Name:");
        String firstName = sc.next();
        System.out.println("Enter Last Name:");
        String lastName = sc.next();
        System.out.println("Enter address:");
        sc.nextLine();
        String address = sc.nextLine();
        System.out.println("Enter city:");
        String city = sc.next();
        System.out.println("Enter state:");
        sc.nextLine();
        String state = sc.nextLine();
        System.out.println("Enter zip:");
        int zip = sc.nextInt();
        System.out.println("Enter phone No.:");
        String phoneNumber = sc.next();
        System.out.println("Enter email address:");
        String email = sc.next();
        return (new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email));
    }

    public HashSet<String> searchContactByCity(String city) {
        contactsByCity.clear();
        addressBook.stream().filter(contact -> city.equalsIgnoreCase(contact.getCity()))
                .forEach(contact -> contactsByCity.add(contact.getFullName()));
        return contactsByCity;
    }

    public HashSet<String> searchContactByState(String state) {
        contactsByState.clear();
        addressBook.stream().filter(contact -> state.equalsIgnoreCase(contact.getState()))
                .forEach(contact -> contactsByState.add(contact.getFullName()));
        return contactsByState;
    }

    public void addressBookOperations(String addressBookNameToOperate) {
        Path addressBookFilePath = Paths.get(ADRESS_BOOK_FILES + "/" + addressBookNameToOperate + ".txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("Entered Address Book -> " + addressBookNameToOperate);

        boolean operate = true;
        while (operate) {
            System.out.println("1. Read Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Sort Address Book");
            System.out.println("5. Write Address Book");
            System.out.println("6. Exit");
            System.out.println("Enter your choice : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter IO Service : \n1.CONSOLE_IO\n2.FILE_IO");
                    int ioChoice = sc.nextInt();
                    if (ioChoice == 1)
                        addContactToAddressBook(createContact());
                    else if (ioChoice == 2) {
                        AddressBookFileIOService fileReadObject = new AddressBookFileIOService(addressBookFilePath);
                        addressBook = fileReadObject.readData();
                    } else
                        System.out.println("Invalid IO choice selected");
                    break;
                case 2:
                    System.out.println("Enter the Full Name : ");
                    sc.nextLine();
                    String fullName = sc.nextLine();
                    editContact(fullName);
                    break;
                case 3:
                    System.out.println("Enter the Full Name : ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    deleteContact(name);
                    break;
                case 4:
                    System.out.println("Sort by :");
                    System.out.println("1.Person");
                    System.out.println("2.City ");
                    System.out.println("3.State");
                    System.out.println("4.Zip");
                    System.out.println("Enter your choice : ");
                    int sortChoice = sc.nextInt();
                    if (sortChoice == 1)
                        sortByPerson();
                    else if (sortChoice == 2)
                        sortByCity();
                    else if (sortChoice == 3)
                        sortByState();
                    else if (sortChoice == 4)
                        sortByZip();
                    else
                        System.out.println("Invalid parameter for sorting selected!");
                    break;
                case 5:
                    System.out.println("Enter IO Service : \n1.CONSOLE_IO\n2.FILE_IO");
                    int choiceOfIO = sc.nextInt();
                    if (choiceOfIO == 1)
                        displayAddressBook();
                    else if (choiceOfIO == 2) {
                        AddressBookFileIOService fileWriteObject = new AddressBookFileIOService(addressBookFilePath);
                        fileWriteObject.writeData(addressBook);
                    } else
                        System.out.println("Invalid IO choice");
                    break;
                case 6:
                    operate = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    //	SORTING
    public void sortByPerson() {
        addressBook = addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getFullName))
                .collect(Collectors.toList());
        System.out.println("Address Book sorted by Person Name");
    }

    public void sortByCity() {
        addressBook = addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getCity))
                .collect(Collectors.toList());
        System.out.println("Address Book sorted by City");
    }

    public void sortByState() {
        addressBook = addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getState))
                .collect(Collectors.toList());
        System.out.println("Address Book sorted by State");
    }

    public void sortByZip() {
        addressBook = addressBook = addressBook.stream().sorted(Comparator.comparing(Contacts::getZip))
                .collect(Collectors.toList());
        System.out.println("Address Book sorted by Zip");
    }

    public ArrayList<String> getCities() {
        addressBook.stream().forEach(contact -> {
            city.add(contact.getCity());
        });
        return city;
    }

    public ArrayList<String> getStates() {
        addressBook.stream().forEach(contact -> {
            state.add(contact.getState());
        });
        return state;
    }

}