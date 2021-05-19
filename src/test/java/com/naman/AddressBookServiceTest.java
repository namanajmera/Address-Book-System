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
            serviceObject.updateContactEmail("Naman", "Ajmera", "naman.ajmera.com");
            boolean result = serviceObject.checkContactDataInSyncWithDB("Naman", "Ajmera");
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

    @Test
    public void givenCityAndState_WhenRetrieved_ShouldMatchContactsCountPerCityAndPerState() {

        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        int countPerCity = serviceObject.getCountByCity(AddressBookService.IOService.DB_IO, "Lucknow");
        int countPerState = serviceObject.getCountByState(AddressBookService.IOService.DB_IO, "Uttar Pradesh");
        boolean result = countPerCity == 4 && countPerState == 6;
        Assertions.assertTrue(result);
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
        try {
            AddressBookService serviceObject = new AddressBookService();
            serviceObject.readContactData(AddressBookService.IOService.DB_IO);
            serviceObject.addContactToAddressBook("Shreshtra", "Balaji", "4/14 Airport Road", "Mumbai", "Maharashtra", 245245, "addressbooknew@capgemini.com",
                    "9898989898", "TemporaryBook", "Temp");
            boolean result = serviceObject.checkContactDataInSyncWithDB("Shreshtra", "Balaji");
            Assertions.assertTrue(result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}