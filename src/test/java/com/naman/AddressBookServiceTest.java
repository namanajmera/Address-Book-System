package com.naman;

import com.google.gson.Gson;
import com.naman.addressbookservice.AddressBookService;
import com.naman.exception.DBException;
import com.naman.modal.Contacts;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddressBookServiceTest {



    @Test
    public void givenContactsInDB_WhenRetrieved_ShouldMatchContactsCount() {

        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        int countOfEntriesRetrieved = serviceObject.sizeOfContactList();
        Assertions.assertEquals(11, countOfEntriesRetrieved);
    }

    @Test
    public void givenNewEmailForContact_WhenUpdated_ShouldSyncWithDB() {
        try {
            AddressBookService serviceObject = new AddressBookService();
            serviceObject.readContactData(AddressBookService.IOService.DB_IO);
            serviceObject.updateContactEmail("Aditya", "Verma", "addressbook@gmail.com");
            boolean result = serviceObject.checkContactDataInSyncWithDB("Aditya", "Verma");
            Assertions.assertTrue(result);
        }catch (Exception e) {
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
        Assertions.assertEquals(5, employeePayrollData.size());
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
            serviceObject.addContactToAddressBook("Aman", "Singhal","19/11 Akash Road", "Mumbai", "Maharashtra", 458881, "fake@gmail.com",
                    "9966996669","Temp", "TemporaryBook");
            boolean result = serviceObject.checkContactDataInSyncWithDB("Shreshtra", "Balaji");
            Assertions.assertTrue(result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleContacts_WhenAddedToDB_ShouldMatchContactCount() throws InterruptedException {
        AddressBookService serviceObject = new AddressBookService();
        serviceObject.readContactData(AddressBookService.IOService.DB_IO);
        Contacts[] arrayOfContacts = {
                new Contacts(0,"Morri", "Singhal","10/12 Moti Nagar", "Kanpur", "Uttar Pradesh", 215881, "addressbooknew6@gmail.com","9888886669","Temp", "TemporaryBook"),
                new Contacts(0,"Tapas", "Singh","2/1 Hamesh Road", "Lucknow", "Uttar Pradesh", 422581, "addressbooknew7@gmail.com","9977776669","Temp", "TemporaryBook"),
                new Contacts(0,"Alok", "Balaji","4/6 Airport Road", "Mumbai", "Maharashtra", 458456, "addressbooknew8@gmail.com","9966955559","Temp", "TemporaryBook"),
                new Contacts(0,"Warren", "Estacaldo","5/120 Dumna Road", "Jabalpur", "Madhya Pradesh", 459851, "addressbooknew9@gmail.com","9964566669","Temp", "TemporaryBook")
        };
        Instant start = Instant.now();
        serviceObject.addContactListToAddressBook(Arrays.asList(arrayOfContacts));
        Instant end = Instant.now();
        Thread.sleep(20);
        System.out.println("Duration with Threading : " + Duration.between(start, end));

        Assertions.assertEquals(15, serviceObject.sizeOfContactList());
    }

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Contacts[] getContactsList() {
        Response response = RestAssured.get("/address-book");
        System.out.println("CONTACT Entries in JSON Server :\n" + response.asString());
        Contacts[] arrayOfContacts = new Gson().fromJson(response.asString(), Contacts[].class);
        return arrayOfContacts;
    }

    public Response addContactToJsonServer(Contacts ContactData) {
        String contactJson = new Gson().toJson(ContactData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(contactJson);
        return request.post("/address-book");
    }

    public Response updateContactEmailInJsonServer(Contacts contactData) {
        String contactJson = new Gson().toJson(contactData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(contactJson);
        return request.put("/address-book/" + contactData.getId());
    }

    public Response deleteContactFromJsonServer(Contacts contactData) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        return request.delete("/address-book/" + contactData.getId());
    }

    @Test
    public void givenContactDataInJsonServer_WhenRetrieved_ShouldMatchContactCount() {
        Contacts[] arrayOfContacts = getContactsList();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        long entries = serviceObject.sizeOfContactList();
        Assertions.assertEquals(16, entries);
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldMatchContactCount() {
        Contacts[] arrayOfContacts = getContactsList();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        Contacts newContact = new Contacts(0,"Aman", "Singhal","19/11 Akash Road", "Mumbai", "Maharashtra", 458881, "fake@gmail.com",
                "9966996669","Temp", "TemporaryBook");
        Response response = addContactToJsonServer(newContact);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(201, statusCode);

        newContact = new Gson().fromJson(response.asString(), Contacts.class);
        serviceObject.addContactToAddressBook(newContact);
        long entries = serviceObject.sizeOfContactList();
        Assertions.assertEquals(16, entries);
    }

    @Test
    public void givenMultipleContacts_WhenAddedToJSONServer_ShouldMatchContactCountAndSyncWithMemory() {
        Contacts[] arrayOfContacts = getContactsList();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        Contacts[] arrayOfNewContacts = {
                new Contacts(0,"Morri", "Singhal","10/12 Moti Nagar", "Kanpur", "Uttar Pradesh", 215881, "addressbooknew6@gmail.com","9888886669","Temp", "TemporaryBook"),
                new Contacts(0,"Tapas", "Singh","2/1 Hamesh Road", "Lucknow", "Uttar Pradesh", 422581, "addressbooknew7@gmail.com","9977776669","Temp", "TemporaryBook"),
                new Contacts(0,"Alok", "Balaji","4/6 Airport Road", "Mumbai", "Maharashtra", 458456, "addressbooknew8@gmail.com","9966955559","Temp", "TemporaryBook"),
                new Contacts(0,"Warren", "Estacaldo","5/120 Dumna Road", "Jabalpur", "Madhya Pradesh", 459851, "addressbooknew9@gmail.com","9964566669","Temp", "TemporaryBook")
        };
        try {
            for(Contacts newContact : arrayOfNewContacts) {
                Response response = addContactToJsonServer(newContact);
                int statusCode = response.getStatusCode();
                Assertions.assertEquals(201, statusCode);

                newContact = new Gson().fromJson(response.asString(), Contacts.class);
                serviceObject.addContactToAddressBook(newContact);
                Assertions.assertTrue(serviceObject.checkContactDataInSyncWithDB(newContact.getFirstName(), newContact.getLastName()));
            }
            long entries = serviceObject.sizeOfContactList();
            Assertions.assertEquals(20, entries);
        }catch (DBException e) {
        }
    }

    @Test
    public void givenNewEmailForContact_WhenUpdatedInJSONServer_ShouldMatch200ResponseAndSyncWithDB() {
        Contacts[] arrayOfContacts = getContactsList();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        try {
            serviceObject.updateContactEmail("Warren", "Estacaldo", "updatedemail@gmail.com");
            Contacts contactData = serviceObject.getContactData("Warren", "Estacaldo");

            Response response = updateContactEmailInJsonServer(contactData);
            int statusCode = response.getStatusCode();
            Assertions.assertEquals(200, statusCode);

            Contacts updatedContact = new Gson().fromJson(response.asString(), Contacts.class);
            boolean result = serviceObject.checkContactDataInSyncWithDB(updatedContact.getFirstName(), updatedContact.getLastName());
            Assertions.assertTrue(result);
        }catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenContact_WhenDeletedFromJSONServer_ShouldMatch200ResponseAndContactCountAndSyncWithDB() {
        Contacts[] arrayOfContacts = getContactsList();
        AddressBookService serviceObject = new AddressBookService(Arrays.asList(arrayOfContacts));
        try {
            Contacts contactData = serviceObject.getContactData("Warren", "Estacaldo");
            Response response = deleteContactFromJsonServer(contactData);
            int statusCode = response.getStatusCode();
            Assertions.assertEquals(200, statusCode);

            serviceObject.deleteContactData("Warren", "Estacaldo");
            long entries = serviceObject.sizeOfContactList();
            Assertions.assertEquals(19, entries);

            boolean result = serviceObject.checkContactDataInSyncWithDB("Warren", "Estacaldo");
            Assertions.assertTrue(result);
        }catch (DBException e) {
            e.printStackTrace();
        }
    }
}