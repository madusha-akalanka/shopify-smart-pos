package org.lk.pos.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDataAccess {

    private static  final PreparedStatement STM_EXISTS_BY_CUSTOMER_ID;
    private static final PreparedStatement STM_EXISTS_BY_ITEM_CODE;

    static {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            STM_EXISTS_BY_CUSTOMER_ID=connection.prepareStatement("SELECT * FROM \"order\" WHERE customer_id=?");
        STM_EXISTS_BY_ITEM_CODE=connection.prepareStatement("SELECT * FROM order_item WHERE item_code=? ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsOrderByCustomerId(String customerId){
        try {
            STM_EXISTS_BY_CUSTOMER_ID.setString(1,customerId);
            return STM_EXISTS_BY_CUSTOMER_ID.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean existOrderByItemCode(String code) throws SQLException {

        STM_EXISTS_BY_ITEM_CODE.setString(1,code);
        return STM_EXISTS_BY_ITEM_CODE.executeQuery().next();

    }



}
