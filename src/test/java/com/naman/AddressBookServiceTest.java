package com.naman;

import com.naman.addressbookservice.AddressBookService;
import com.naman.modal.Contacts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class AddressBookServiceTest {
    @Test
    public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() {
        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        int countOfEntriesRetrieved = serviceObject.sizeOfContactList();
        Assertions.assertEquals(5, countOfEntriesRetrieved);
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

    @Test
    public void givenDateRange_WhenContactsRetrieved_ShouldMatchContactsCount() {

        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<Contacts> employeePayrollData = serviceObject.readContactsForDateRange(AddressBookService.IOService.DB_IO, startDate, endDate);
        Assertions.assertEquals(4, employeePayrollData.size());
    }
}