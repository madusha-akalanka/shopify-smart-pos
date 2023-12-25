package org.lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ManageItemFormController {
    public AnchorPane root;
    public ImageView imgHome;
    public JFXButton btnAddNew;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView tblItems;

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        MainFormController.navigateToMain(root);
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {

        for(TextField textField:new TextField[]{txtCode,txtDescription,txtQtyOnHand,txtUnitPrice}){
            textField.clear();
            txtCode.requestFocus();
            tblItems.getSelectionModel().clearSelection();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
