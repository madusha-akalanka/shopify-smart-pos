package org.lk.pos.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lk.pos.tm.Customer;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDataAccessTest {

    @BeforeEach
    void setUp() {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void tearDown() {

        try {
            DbConnection.getInstance().getConnection().rollback();
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void sqlSyntax(){

        assertDoesNotThrow(()->Class.forName("org.lk.pos.db.CustomerDataAccess"));

    }



    @Test
    void getAlLCustomers() throws SQLException {


        CustomerDataAccess.saveCustomer(new Customer("001","Kasun","Galle"));
        CustomerDataAccess.saveCustomer(new Customer("002","Nimal","Galle"));

        assertDoesNotThrow(()->{
            List<Customer> customerList = CustomerDataAccess.getAlLCustomers();
            assertTrue(customerList.size()>=2);
        });
    }

    @Test
    void saveCustomer() {

        assertDoesNotThrow(()->{
            CustomerDataAccess.saveCustomer(new Customer("001","Kasun","Galle"));
            CustomerDataAccess.saveCustomer(new Customer("002","Ruwan","Colombo"));
        });
        assertThrows(SQLException.class,()->{
            CustomerDataAccess.saveCustomer(new Customer("001","Kasun","Galle"));
        });
    }

    @Test
    void updateCustomer() throws SQLException {
        CustomerDataAccess.saveCustomer(new Customer("001","Kasun","Galle"));
        assertDoesNotThrow(()->{
            CustomerDataAccess.updateCustomer(new Customer("001","Nuwan","Matara"));
        });
    }

    @Test
    void deleteCustomer() throws SQLException {

        CustomerDataAccess.saveCustomer(new Customer("001","Kasun","Galle"));
        int size=CustomerDataAccess.getAlLCustomers().size();
        assertDoesNotThrow(()->{
            CustomerDataAccess.deleteCustomer("001");
            assertEquals(size-1,CustomerDataAccess.getAlLCustomers().size());
        });

    }

    @Test
    void getLastCustomerId() throws SQLException {

        String lastCustomerId = CustomerDataAccess.getLastCustomerId();
        if(CustomerDataAccess.getAlLCustomers().isEmpty()){

            assertNull(lastCustomerId);
        }else{
            CustomerDataAccess.saveCustomer(new Customer("001","Kasun","galle"));
            lastCustomerId = CustomerDataAccess.getLastCustomerId();
            assertNotNull(lastCustomerId);
        }
    }
}