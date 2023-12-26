package org.lk.pos.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lk.pos.tm.Item;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDataAccessTest {
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
    void saveItems() {

        assertDoesNotThrow(() -> {

            ItemDataAccess.saveItems(new Item("1111", "Pencil", 15, new BigDecimal("23.63")));
            ItemDataAccess.saveItems(new Item("2222", "Book", 15, new BigDecimal("523.13")));
        });
           //Test Exist Customer
        assertThrows(SQLException.class, () -> {
            ItemDataAccess.saveItems(new Item("1111", "Pencil", 15, new BigDecimal("23.63")));
        });


    }


    @Test
    void updateItems() {

        assertDoesNotThrow(() -> {
            ItemDataAccess.saveItems(new Item("0001", "Pencil", 15, new BigDecimal("23.63")));
            ItemDataAccess.saveItems(new Item("0002", "Book", 35, new BigDecimal("283.12")));
            ItemDataAccess.updateItems(new Item("0001", "Shoes", 195, new BigDecimal("2228.99")));
        });





    }


    @Test
    void deleteItem() throws SQLException {
        ItemDataAccess.saveItems(new Item("1111", "Pencil", 15, new BigDecimal("25.36")));
        int size = ItemDataAccess.getAllItems().size();
        assertDoesNotThrow(() -> {
            ItemDataAccess.deleteItem("1111");
            assertEquals(size - 1, ItemDataAccess.getAllItems().size());


        });
    }
}
