package com.naman.ioservice;

import com.naman.exception.DBException;
import com.naman.modal.Contacts;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDBIOService {

    private PreparedStatement addressBookDataStatement;
    private static AddressBookDBIOService addressBookDBService;

    private AddressBookDBIOService() {
    }

    public static AddressBookDBIOService getInstatnce() {
        if(addressBookDBService == null)
            addressBookDBService = new AddressBookDBIOService();
        return addressBookDBService;
    }

    private Connection establishConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3307/address_book_service";
        String userName = "root";
        String password = "Spider@6426";
        System.out.println("Establishing connection to database : " + jdbcURL);
        return DriverManager.getConnection(jdbcURL, userName, password);
    }

    private void prepareStatementForContactData() throws DBException {
        String sql = "select * from contact where first_name = ? and last_name = ?;";
        try {
            Connection connection = this.establishConnection();
            this.addressBookDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e) {
            throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
        }
    }

    public List<Contacts> getEmplyoeePayrollDataUsingName(String firstName, String lastName) throws DBException {
        List<Contacts> contactDataList = null;
        if(this.addressBookDataStatement == null)
            this.prepareStatementForContactData();
        try {
            addressBookDataStatement.setString(1, firstName);
            addressBookDataStatement.setString(2, lastName);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            contactDataList = this.getContactDataUsingResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Cannot execute query", DBException.ExceptionType.SQL_ERROR);
        }
        return contactDataList;
    }

    public List<Contacts> readContactsForDateRange(LocalDate startDate, LocalDate endDate) throws DBException {
        String sql = String.format("select distinct contact.first_name as first_name, contact.last_name as last_name, contact.address as address, "
                + "contact.city as city, contact.state as state, contact.zip as zip, contact.email as email "
                + "from contact "
                + "inner join contact_book on contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
                + "where contact_book.date_added between '%s' and '%s' ;", Date.valueOf(startDate),Date.valueOf(endDate));
        return this.getContactDataUsingDB(sql);
    }

    public Map<String, Integer> getCountByCity() throws DBException {
        String sql = "select city, count(*) as contacts_per_city from contact group by city;";
        Map<String, Integer> cityToContactCountMap = new HashMap<>();
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                cityToContactCountMap.put(resultSet.getString("city"), resultSet.getInt("contacts_per_city"));
            }
        }catch (SQLException e) {
            throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
        }
        return cityToContactCountMap;
    }

    public Map<String, Integer> getCountByState() throws DBException {
        String sql = "select state, count(*) as contacts_per_state from contact group by state;";
        Map<String, Integer> stateToContactCountMap = new HashMap<>();
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                stateToContactCountMap.put(resultSet.getString("state"), resultSet.getInt("contacts_per_state"));
            }
        }catch (SQLException e) {
            throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
        }
        return stateToContactCountMap;
    }

    public List<Contacts> readData() throws DBException {
        String sql = "select * from contact;";
        return this.getContactDataUsingDB(sql);
    }

    private List<Contacts> getContactDataUsingDB(String sql) throws DBException {
        List<Contacts> contactDataList = null;
        try {
            Connection connection = this.establishConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactDataList = this.getContactDataUsingResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
        }
        return contactDataList;
    }

    private List<Contacts> getContactDataUsingResultSet(ResultSet resultSet) throws SQLException {
        List<Contacts> contactDataList = new ArrayList<>();
        while (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            int zip = resultSet.getInt("zip");
            String email = resultSet.getString("email");

            List<String> phoneList = new ArrayList<>();
            Map<String, String> addressBooks = new HashMap<>();

            try (Connection connection = this.establishConnection()) {
                String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, contact_number.phone as phone "
                        + "from contact "
                        + "inner join contact_number on contact.first_name = contact_number.first_name and contact.last_name = contact_number.last_name "
                        + "where contact.first_name = '%s' and contact.last_name = '%s';", firstName,lastName);
                Statement statement = connection.createStatement();
                ResultSet resultSetForContactNumber = statement.executeQuery(sql);
                while (resultSetForContactNumber.next()) {
                    phoneList.add(resultSetForContactNumber.getString("phone"));
                }
            }
            try (Connection connection = this.establishConnection()) {
                String sql = String.format("select contact.first_name as first_name, contact.last_name as last_name, "
                        + "address_book.name as address_book_name,contact_book.type as type "
                        + "from contact "
                        + "inner join contact_book on contact.first_name = contact_book.first_name and contact.last_name = contact_book.last_name "
                        + "inner join address_book on contact_book.type = address_book.type "
                        + "where contact.first_name = '%s' and contact.last_name = '%s';",firstName,lastName);
                Statement statement = connection.createStatement();
                ResultSet resultSetForAddressBookData = statement.executeQuery(sql);
                while (resultSetForAddressBookData.next()) {
                    addressBooks.put(resultSetForAddressBookData.getString("type"), resultSetForAddressBookData.getString("address_book_name"));
                }
            }
            contactDataList
                    .add(new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBooks));
        }
        return contactDataList;
    }

    public int updateContactEmail(String firstName, String lastName, String email) throws DBException {
        String sql = String.format("update contact set email = '%s' where first_name = '%s' and last_name = '%s' ;", email, firstName, lastName);
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
        }
    }

    public Contacts addContactToAddressBook(String firstName, String lastName, String address, String city,
                                            String state, int zip, String email, String phone, String addressBookName, String type) throws DBException {
        List<String> phoneList = new ArrayList<>();
        Map<String, String> addressBooks = new HashMap<>();
        Contacts newContact = null;
        Connection connection = null;
        try {
            connection = this.establishConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            String sqlToRetrieveAddressBookNameAndType = "select address_book.name as address_book_name, address_book.type as type "
                    + "from address_book ";
            ResultSet resultSetToRetrieveAddressBookNameAndType = statement.executeQuery(sqlToRetrieveAddressBookNameAndType);
            while(resultSetToRetrieveAddressBookNameAndType.next()) {
                addressBooks.put(resultSetToRetrieveAddressBookNameAndType.getString("type"),
                        resultSetToRetrieveAddressBookNameAndType.getString("address_book_name"));
            }
            if(!addressBooks.containsKey(type)) {
                String sqlToInsert = String.format("insert into address_book (name, type) values  ('%s','%s');", addressBookName, type);
                int addressBookRowsUpdated = statement.executeUpdate(sqlToInsert, statement.RETURN_GENERATED_KEYS);
                if(addressBookRowsUpdated == 1) {
                    addressBooks.put(addressBookName, type);
                }
                System.out.println("Inserted into Address Book");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            boolean isExists = false;
            String sqlToRetrieveContactName =
                    String.format("select first_name, last_name from contact where first_name = '%s' and last_name = '%s';", firstName, lastName);
            Statement statementToRetrieveContactName = connection.createStatement();
            ResultSet resultSetToRetrieveContactName = statementToRetrieveContactName.executeQuery(sqlToRetrieveContactName);
            if(resultSetToRetrieveContactName.next()) {
                isExists = true;
            }
            if(!isExists){
                String sqlToInsert = String.format(
                        "insert into contact " + "(first_name, last_name, address, city, state, zip, email) values "
                                + "('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        firstName, lastName, address, city, state, zip, email);
                int contactRowsUpdated = statement.executeUpdate(sqlToInsert);
                System.out.println("Inserted into Contact");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            String sqlToRetrievePhone =
                    String.format("select first_name, last_name, phone "
                            + "from contact_number "
                            + "where first_name = '%s' and last_name = '%s' ;", firstName, lastName);
            ResultSet resultSetToRetrievePhone = statement.executeQuery(sqlToRetrievePhone);
            while(resultSetToRetrievePhone.next()) {
                phoneList.add(resultSetToRetrievePhone.getString("phone"));
            }
            if(!phoneList.contains(phone)) {
                String sqlToInsert = String.format("insert into contact_number "
                        + "(first_name, last_name, phone) values ('%s','%s','%s');", firstName, lastName, phone);
                int contactNumberRowsUpdated = statement.executeUpdate(sqlToInsert, statement.RETURN_GENERATED_KEYS);
                if(contactNumberRowsUpdated == 1) {
                    phoneList.add(phone);
                }
                System.out.println("Inserted into Contact Number");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            boolean isExists = false;
            String sqlToRetrieveContactNameAndBookType =
                    String.format("select first_name, last_name, type "
                            + "from contact_book "
                            + "where first_name = '%s' and last_name = '%s' and type = '%s';", firstName, lastName, type);
            Statement statementToRetrieveContactNameAndBookType = connection.createStatement();
            ResultSet resultSetRetrieveContactNameAndBookType = statementToRetrieveContactNameAndBookType.executeQuery(sqlToRetrieveContactNameAndBookType);
            if(resultSetRetrieveContactNameAndBookType.next()) {
                isExists = true;
            }
            if(!isExists){
                String sqlToInsert = String.format(
                        "insert into contact_book "
                                +"(type, first_name, last_name) values ('%s', '%s', '%s')", type, firstName, lastName);
                int contactBookRowsUpdated = statement.executeUpdate(sqlToInsert);
                if(contactBookRowsUpdated == 1) {
                    newContact = new Contacts(firstName, lastName, address, city, state, zip, email, phoneList, addressBooks);
                    System.out.println("Inserted into Contact Book");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newContact;
    }
}