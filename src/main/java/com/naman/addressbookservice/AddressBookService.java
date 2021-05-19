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
}
