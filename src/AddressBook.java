import java.util.Scanner;

public class AddressBook {
	public static void main(String[] args) {
        System.out.println("Welcome to the Address Book...!!!");
        ContactPerson contactPerson = new ContactPerson();
        // List<ContactPersonUC2> contact = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Address Book Menu");
        boolean isAlive = true;
        while (isAlive) {
            System.out.println("Enter choice: ");
            System.out.println("1. For Add a new Contact to AddressBook.");
            System.out.println("2. For Display Contact Detial.");
            System.out.println("3. For Exit.");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 1:
                System.out.println("Enter the first_name:-");
                contactPerson.setFirst_name(sc.nextLine());
                System.out.println("Enter the last_name:-");
                contactPerson.setLast_name(sc.nextLine());
                System.out.println("Enter the address_name:-");
                contactPerson.setAddress(sc.nextLine());
                System.out.println("Enter the city_name:-");
                contactPerson.setCity(sc.nextLine());
                System.out.println("Enter the state_name:-");
                contactPerson.setState(sc.nextLine());
                System.out.println("Enter the zip_number:-");
                contactPerson.setZip(sc.nextInt());
                System.out.println("Enter the phone_number:-");
                contactPerson.setPhone_number(sc.nextLong());
                sc.nextLine();
                System.out.println("Enter the email:-");
                contactPerson.setEmail(sc.nextLine());
                isAlive = true;

                // contact.add(new ContactPersonUC2(contactPerson.getFirst_name(),
                // contactPerson.getLast_name(),
                // contactPerson.getAddress(), contactPerson.getCity(),
                // contactPerson.getState(),
                // contactPerson.getZip(), contactPerson.getPhone_number(),
                // contactPerson.getEmail()));
                break;

            case 2:
                System.out.println(contactPerson.toString());
                System.out.println("Contact is added successfully.");
                break;

            case 3:
                isAlive = false;
                System.out.println("Exit.....");
                break;

            default:
                System.out.println("You have entered wrong choice.");
                isAlive = true;
                break;
            }
        }
        sc.close();
    }
}


