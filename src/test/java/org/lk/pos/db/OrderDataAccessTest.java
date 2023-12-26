package org.lk.pos.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lk.pos.tm.Customer;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderDataAccessTest {
    @BeforeEach
    void setUp() throws SQLException {

        DbConnection.getInstance().getConnection().setAutoCommit(false);

    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnection.getInstance().getConnection().rollback();
        DbConnection.getInstance().getConnection().setAutoCommit(true);
    }

    @Test
    void existsOrderByCustomerId() throws SQLException {

        assertDoesNotThrow(()->{
            assertFalse(OrderDataAccess.existsOrderByCustomerId("C001"));
        });
        CustomerDataAccess.saveCustomer(new Customer("C001","Nimal","Panadura"));
        DbConnection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO \"order\"(id,customer_id) VALUES('O001','C001')");
        assertDoesNotThrow(()->assertTrue(OrderDataAccess.existsOrderByCustomerId("C001")));
    }
}