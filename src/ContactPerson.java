
public class ContactPerson {
    private String firstName;
    private String lastName;
    private String area;
    private String city;
    private String state;
    private int pin;
    private int phoneNumber;
    private String email;

    ContactPerson(String first_name, String last_name, String address, String city, String state, int zip,
            long phone_number, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    ContactPerson() {
        this(" ", " ", " ", " ", " ", 0, 0, " ");
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lastName;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String ar) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String cty) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String st) {
        this.state = state;
    }

    public int getPin() {
        return this.pin;
    }

    public void setZip(int zp) {
        this.pin = pin;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phn) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String eml) {
        this.email = email;
    }

    public String toString() {
        return "Details of: " + firstName + " " + lastName + "\n" + "Area: " + area + "\n" + "City: " + city + "\n"
                + "State: " + state + "\n" + "pin: " + pin + "\n" + "Phone Number: " + phoneNumber + "\n" + "Email: "
                + email;
    }

    // public void setPin(int nextInt) {

    // }

}
