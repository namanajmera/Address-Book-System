import java.util.ArrayList;
import java.util.Scanner;

public class AddressBookDetails {
    public String addressBookName;
    public String firstName;
    public String lastName;
    public String area, city, state, email;
    public int pin, phoneNumber;
    public static int indexNum;

    public AddressBookDetails(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public static ArrayList<ContactPerson> list = new ArrayList<ContactPerson>();

    public Scanner sc = new Scanner(System.in);

    public boolean checkName() {
        System.out.println("Enter First Name");
        firstName = sc.next();

        System.out.println("Enter Last Name");
        lastName = sc.next();

        for (indexNum = 0; indexNum < list.size(); indexNum++) {
            if (firstName.equals(list.get(indexNum).getFirstName())
                    && lastName.equals(list.get(indexNum).getLastName())) {
                System.out.println("Contact Name Exists");
                return true;
            }
        }
        return false;
    }

    public void addDetails() {
        if (!checkName()) {
            System.out.println("Enter Area");
            area = sc.next();

            System.out.println("Enter CityName");
            city = sc.next();

            System.out.println("Enter StateName");
            state = sc.next();

            System.out.println("Enter PinCode");
            pin = sc.nextInt();

            System.out.println("Enter PhoneNumber");
            phoneNumber = sc.nextInt();

            System.out.println("Enter Email");
            email = sc.next();
        }
        list.add(new ContactPerson(firstName, lastName, area, city, state, pin, phoneNumber, email));
    }

    public String editDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Details to be Edited: ");
        if (checkName()) {
            System.out.println("Enter FirstName");
            list.get(indexNum).setFirstName(sc.next());

            System.out.println("Enter LastName");
            list.get(indexNum).setLastName(sc.next());

            System.out.println("Enter Area");
            list.get(indexNum).setArea(sc.next());

            System.out.println("Enter CityName");
            list.get(indexNum).setCity(sc.next());

            System.out.println("Enter StateName");
            list.get(indexNum).setState(sc.next());

            System.out.println("Enter pinCode");
            list.get(indexNum).setZip(sc.nextInt());

            System.out.println("Enter PhoneNumber");
            list.get(indexNum).setPhoneNumber(sc.nextInt());

            System.out.println("Enter Email");
            list.get(indexNum).setEmail(sc.next());
            return "Edited";
        }
        return "Name Not Available in List";
    }

    public String deleteDetails() {
        System.out.print("Details to be Deleted");
        if (checkName()) {
            list.remove(indexNum);
            return "Deleted";
        }
        return "Name Not Available in List";
    }

    public void displayDetails() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println();
            System.out.println(list.get(i));
        }
    }

    public String toString() {
        return addressBookName;
    }
}
