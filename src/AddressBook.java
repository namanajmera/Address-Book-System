import java.util.Scanner;

public class AddressBook {
<<<<<<< HEAD
<<<<<<< HEAD
	
	public static void main(String[] args) {
		ContactPerson contactPerson = new ContactPerson();
||||||| 21e3082
	
	public static void main(String[] args) {
		ContactPerson contactPerson = new ContactPerson();
=======

    public static void main(String[] args) {
        ContactPerson contactPerson = new ContactPerson();
>>>>>>> UC4-Delete-Contact
        // List<ContactPersonUC2> contact = new ArrayList<>();;;;
||||||| 0c11299
	
	public static void main(String[] args) {
		ContactPerson contactPerson = new ContactPerson();
        // List<ContactPersonUC2> contact = new ArrayList<>();
=======

    public static void main(String[] args) {
        ContactPerson contactPerson = new ContactPerson();
        // List<ContactPersonUC2> contact = new ArrayList<>();
>>>>>>> UC3-Update-Contact
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Address Book Menu");
        boolean isAlive = true;

        while (isAlive) {
            System.out.println();
            System.out.println("Enter choice: ");
            System.out.println("1. For Add a new Contact to AddressBook.");
            System.out.println("2. For Display Contact Detial.");
            System.out.println("3. For Updating Contact Detial.");
            System.out.println("4. For Deleting Contact Detial.");
            System.out.println("5. For Exit.");
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
                System.out.println("Contact is added successfully.");

                // contact.add(new ContactPersonUC2(contactPerson.getFirst_name(),
                // contactPerson.getLast_name(),
                // contactPerson.getAddress(), contactPerson.getCity(),
                // contactPerson.getState(),
                // contactPerson.getZip(), contactPerson.getPhone_number(),
                // contactPerson.getEmail()));
                break;

            case 2:
                if (contactPerson.getFirst_name() == " " && contactPerson.getLast_name() == " "
                        && contactPerson.getAddress() == " " && contactPerson.getCity() == " "
                        && contactPerson.getState() == " " && contactPerson.getZip() == 0
                        && contactPerson.getPhone_number() == 0 && contactPerson.getEmail() == " ") {
                    System.out.println("Address book is Empty. Firstly add the contact.");
                    break;
                } else {
                    System.out.println(contactPerson.toString());
                }
                break;

            case 3:
                if (contactPerson.getFirst_name() == " " && contactPerson.getLast_name() == " "
                        && contactPerson.getAddress() == " " && contactPerson.getCity() == " "
                        && contactPerson.getState() == " " && contactPerson.getZip() == 0
                        && contactPerson.getPhone_number() == 0 && contactPerson.getEmail() == " ") {
                    System.out.println("Address book is Empty. Firstly add the contact.");
                    break;
                } else {
                    System.out.println("Enter the name you want to update:- ");
                    String name = sc.nextLine();
                    if (name.equalsIgnoreCase(contactPerson.getFirst_name())) {
                        boolean isUpdate = true;
                        while (isUpdate) {
                            System.out.println(contactPerson.toString());
                            System.out.print("Enter the index number you want to update form 0 to 7:  ");
                            int index = sc.nextInt();
                            sc.nextLine();
                            switch (index) {
                            case 0:
                                System.out.println("Update the first_name:-");
                                contactPerson.setFirst_name(sc.nextLine());
                                break;
                            case 1:
                                System.out.println("Update the last_name:-");
                                contactPerson.setLast_name(sc.nextLine());
                                break;
                            case 2:
                                System.out.println("Update the address:-");
                                contactPerson.setAddress(sc.nextLine());
                                break;
                            case 3:
                                System.out.println("Update the city:-");
                                contactPerson.setCity(sc.nextLine());
                                break;
                            case 4:
                                System.out.println("Update the state:-");
                                contactPerson.setState(sc.nextLine());
                                break;
                            case 5:
                                System.out.println("Update the zip:-");
                                contactPerson.setZip(sc.nextInt());
                                break;
                            case 6:
                                System.out.println("Update the phone_number:-");
                                contactPerson.setPhone_number(sc.nextLong());
                                sc.nextLine();
                                break;
                            case 7:
                                System.out.println("Update the email_id:-");
                                contactPerson.setEmail(sc.nextLine());
                                break;

                            default:
                                System.out.println("You entered the wrong choice.");
                                break;
                            }
                            System.out.println("Want to update more..?? Yes or No");
                            String ans = sc.next();
                            String yes = "Yes";
                            String no = "No";
                            if (ans.equalsIgnoreCase(yes)) {
                                isUpdate = true;
                            } else if (ans.equalsIgnoreCase(no)) {
                                isUpdate = false;
                            } else {
                                System.out.print("You Entered wrong input: ");
                                isUpdate = true;
                            }
                        }
                    } else {
                        System.out.println("Name not found in contact list.");
                    }
                }
                break;

            case 4:
                if (contactPerson.getFirst_name() == " " && contactPerson.getLast_name() == " "
                        && contactPerson.getAddress() == " " && contactPerson.getCity() == " "
                        && contactPerson.getState() == " " && contactPerson.getZip() == 0
                        && contactPerson.getPhone_number() == 0 && contactPerson.getEmail() == " ") {
                    System.out.println("Address book is Already Empty.");
                    break;
                } else {
                    System.out.println("Enter the name you want to update:- ");
                    String name = sc.nextLine();
                    if (name.equalsIgnoreCase(contactPerson.getFirst_name())) {
                        contactPerson.setFirst_name(" ");
                        contactPerson.setLast_name(" ");
                        contactPerson.setAddress(" ");
                        contactPerson.setCity(" ");
                        contactPerson.setState(" ");
                        contactPerson.setZip(0);
                        contactPerson.setPhone_number(0);
                        contactPerson.setEmail(" ");

                        System.out.println("Contact is deleted.");
                    } else {
                        System.out.println("There is no contact with " + name + ".");
                    }
                }
                break;

            case 5:
                isAlive = false;
                System.out.println("Exit.....");
                break;

            default:
                System.out.println("You have entered wrong choice.");
                break;
            }
        }
        sc.close();
    }
}
