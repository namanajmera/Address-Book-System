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
}
