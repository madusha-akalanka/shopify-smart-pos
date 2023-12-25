package org.lk.pos.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DbConnectionTest {

    @Test
    void getConnection() {
        assertDoesNotThrow(DbConnection.getInstance()::getConnection);


    }

    @Test
    void getInstance() {
        DbConnection instance1 = DbConnection.getInstance();
        DbConnection instance2 = DbConnection.getInstance();
        DbConnection instance3 = DbConnection.getInstance();
        assertEquals(instance1,instance2);
        assertEquals(instance1,instance3);
    }

    @Test
    void generateTestSchema() {

            assertDoesNotThrow(()->{
                DbConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer,item,\"order\",order_item");
            });

    }
}