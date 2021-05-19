package com.naman;

import com.naman.addressbookservice.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressBookServiceTest {
    @Test
    public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() {
        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        int countOfEntriesRetrieved = serviceObject.sizeOfContactList();
        Assertions.assertEquals(0, countOfEntriesRetrieved);
    }

    @Test
    public void givenNewEmailForContact_WhenUpdated_ShouldSyncWithDB() {
        try {
            AddressBookService serviceObject = new AddressBookService();
            serviceObject.readContactData(AddressBookService.IOService.DB_IO);
            serviceObject.updateContactEmail("Aditya", "Verma", "addressbook@capgemini.com");
            boolean result = serviceObject.checkContactDataInSyncWithDB("Aditya", "Verma");
            Assertions.assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}