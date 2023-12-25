package org.lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PlaceOrderFormController {
    public AnchorPane root;
    public ImageView imgHome;
    public TextField txtCustomerName;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableColumn tblOrderDetails;
    public TextField txtUnitPrice;
    public ComboBox cmbCustomerId;
    public ComboBox cmbItemCode;
    public JFXButton btnPlaceOrder;
    public JFXButton btnAddNew;



    public void navigateToHome(MouseEvent mouseEvent) throws IOException {

        MainFormController.navigateToMain(root);
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }
}
