package com.naman.addressbookservice;

import com.naman.exception.DBException;
import com.naman.ioservice.AddressBookDBIOService;
import com.naman.modal.Contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookService {

    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO, REST_IO
    }

    public static final Scanner SC = new Scanner(System.in);
    private List<Contacts> contactDataList;
    private AddressBookDBIOService addressBookDBService;

    public AddressBookService() {
        this.contactDataList = new ArrayList<Contacts>();
        addressBookDBService = AddressBookDBIOService.getInstatnce();
    }

    public AddressBookService(List<Contacts> employeeList) {
        this();
        this.contactDataList = employeeList;
    }

    public int sizeOfContactList() {
        return this.contactDataList.size();
    }

    public void readContactData(IOService ioType) {
        if (ioType.equals(IOService.DB_IO)) {
            try {
                this.contactDataList = addressBookDBService.readData();
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    private Contacts getContactData(String firstName, String lastName) {
        return this.contactDataList.stream()
                .filter(contact -> contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public void updateContactEmail(String firstName, String lastName, String email) throws DBException {
        int result = addressBookDBService.updateContactEmail(firstName, lastName, email);
        if (result == 0)
            throw new DBException("Cannot update the employee payroll data", DBException.ExceptionType.UPDATE_ERROR);
        Contacts contactData = this.getContactData(firstName, lastName);
        if (contactData != null)
            contactData.setEmail(email);
        else
            throw new DBException("Cannot find the employee payroll data", DBException.ExceptionType.INVALID_PAYROLL_DATA);
    }

    public boolean checkContactDataInSyncWithDB(String firstName, String lastName) throws DBException {
        List<Contacts> contactDataList = addressBookDBService.getEmplyoeePayrollDataUsingName(firstName, lastName);
        return contactDataList.get(0).equals(getContactData(firstName, lastName));
    }
}