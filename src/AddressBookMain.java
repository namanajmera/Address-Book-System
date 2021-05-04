import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain {

    private Map<String, AddressBook> addressBookDictionary;

    private Map<String, HashSet<String>> allContactsByCity;
    private Map<String, HashSet<String>> allContactsByState;

    private Map<String, Integer> countContactsByCity;
    private Map<String, Integer> countContactsByState;

    public HashSet<String> cityList = new HashSet<String>();
    public HashSet<String> stateList = new HashSet<String>();

    public AddressBookMain() {
        addressBookDictionary = new HashMap<String, AddressBook>();
        allContactsByCity = new HashMap<String, HashSet<String>>();
        allContactsByState = new HashMap<String, HashSet<String>>();
        countContactsByCity = new HashMap<String, Integer>();
        countContactsByState = new HashMap<String, Integer>();
    }

    public void getAllCities() {
        addressBookDictionary.entrySet().stream().forEach(n -> {
            cityList.addAll(n.getValue().getCities());
        });
    }

    public void getAllStates() {
        addressBookDictionary.entrySet().stream().forEach(n -> {
            stateList.addAll(n.getValue().getStates());
        });
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressBookMain dictionaryObject = new AddressBookMain();
        boolean operation = true;
        while (operation) {
            System.out.println("1. Create and Add AddressBook");
            System.out.println("2. Select AddressBook");
            System.out.println("3. Delete AddressBook");
            System.out.println("4. Display AddressBook");
            System.out.println("5. Display Person");
            System.out.println("6. Exit");
            System.out.println("Enter your choice : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter name of Address Book: ");
                    sc.nextLine();
                    String addressBookName = sc.nextLine();
                    AddressBook addressBookObjectForCreation = new AddressBook();
                    dictionaryObject.addressBookDictionary.put(addressBookName, addressBookObjectForCreation);
                    break;
                case 2:
                    System.out.println("Enter name of Address Book: ");
                    sc.nextLine();
                    String addressBookNameToOperate = sc.nextLine();
                    AddressBook addressBookObjectForOperations = dictionaryObject.addressBookDictionary.get(addressBookNameToOperate);
                    try {
                        addressBookObjectForOperations.addressBookOperations(addressBookNameToOperate);
                        System.out.println("Exited Address Book -> " + addressBookNameToOperate);
                    } catch (NullPointerException e1) {
                        System.out.println("Address Book -> " + addressBookNameToOperate + " doesn't exist in the Dictionary");
                    }
                    break;
                case 3:
                    System.out.println("Enter name of Address Book: ");
                    sc.nextLine();
                    String addressBooknameForDeletion = sc.nextLine();

                    if (dictionaryObject.addressBookDictionary.containsKey(addressBooknameForDeletion)) {
                        dictionaryObject.addressBookDictionary.remove(addressBooknameForDeletion);
                        System.out.println("Address Book Deleted");
                        System.out.println();
                    } else {
                        System.out.println("Address Book -> " + addressBooknameForDeletion + " doesn't exist in the Dictionary");
                    }
                    break;
                case 4:
                    System.out.print("Display All Address Book in the Dictionary (y/n) : ");
                    String option = sc.next();
                    switch (option) {
                        case "y":
                            System.out.println();
                            for (Map.Entry<String, AddressBook> dictionaryInteratorObject : dictionaryObject.addressBookDictionary.entrySet()) {
                                System.out.println();
                                System.out.println("Address Book -> " + dictionaryInteratorObject.getKey());
                                dictionaryInteratorObject.getValue().displayAddressBook();
                            }
                            break;
                        case "n":
                            sc.nextLine();
                            System.out.println();
                            System.out.print("Enter name of Address Book to be displayed: ");
                            String addressBooknameForDisplay = sc.nextLine();
                            try {
                                AddressBook addressBookObjectForDisplay = dictionaryObject.addressBookDictionary.get(addressBooknameForDisplay);
                                addressBookObjectForDisplay.displayAddressBook();
                                System.out.println("Address Book -> " + addressBooknameForDisplay);
                                System.out.println();
                            } catch (NullPointerException e2) {
                                System.out.println("Address Book -> " + addressBooknameForDisplay + " doesn't exist in the Dictionary");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
                case 5:
                    System.out.println("1.Show by City");
                    System.out.println("2.Show by State");
                    System.out.println("3.Show Count by City");
                    System.out.println("4.Show Count by State");
                    System.out.println("Enter your choice :");
                    int showPersonChoice = sc.nextInt();
                    if (showPersonChoice == 1 || showPersonChoice == 3) {
                        dictionaryObject.getAllCities();
                        for (String city : dictionaryObject.cityList) {
                            String key = city;
                            HashSet<String> value = new HashSet<String>();
                            dictionaryObject.addressBookDictionary.entrySet()
                                    .forEach(addressBookIterator -> {
                                        value.addAll(addressBookIterator.getValue().searchContactByCity(key));
                                    });
                            dictionaryObject.allContactsByCity.put(key, value);
                            dictionaryObject.countContactsByCity.put(key, value.size());
                        }
                        if (showPersonChoice == 1)
                            System.out.println(dictionaryObject.allContactsByCity);
                        else
                            System.out.println(dictionaryObject.countContactsByCity);
                    } else if (showPersonChoice == 2 || showPersonChoice == 4) {
                        dictionaryObject.getAllStates();
                        for (String state : dictionaryObject.stateList) {
                            String key = state;
                            HashSet<String> value = new HashSet<String>();
                            dictionaryObject.addressBookDictionary.entrySet()
                                    .forEach(addressBookIterator -> {
                                        value.addAll(addressBookIterator.getValue().searchContactByState(key));
                                    });
                            dictionaryObject.allContactsByState.put(key, value);
                            dictionaryObject.countContactsByState.put(key, value.size());
                        }
                        if (showPersonChoice == 2)
                            System.out.println(dictionaryObject.allContactsByState);
                        else
                            System.out.println(dictionaryObject.countContactsByState);
                    } else {
                        System.out.println("Invalid choice! Can't display Person");
                    }
                    break;
                case 6:
                    operation = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    System.out.println();
            }
        }
    }
}