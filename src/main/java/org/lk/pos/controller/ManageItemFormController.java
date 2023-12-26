package org.lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.lk.pos.db.ItemDataAccess;
import org.lk.pos.db.OrderDataAccess;
import org.lk.pos.tm.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class ManageItemFormController {
    public AnchorPane root;
    public ImageView imgHome;
    public JFXButton btnAddNewItem;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<Item> tblItems;


    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        btnDelete.setDisable(true);
        btnSave.setDefaultButton(true);

        try {
            tblItems.getItems().addAll(ItemDataAccess.getAllItems());
        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "Failed to load items,try later").show();
            e.printStackTrace();
        }
        Platform.runLater(txtCode::requestFocus);

        tblItems.getSelectionModel().selectedItemProperty().addListener((ov, prev, cur) -> {
            if (cur == null) {
                btnSave.setText("Save");
                btnDelete.setDisable(true);
                txtCode.setDisable(false);
            } else {
                btnSave.setText("Update");
                btnDelete.setDisable(false);
                txtCode.setText(cur.getCode());
                txtDescription.setText(cur.getDescription());
                txtQtyOnHand.setText(cur.getQty() + "");
                txtUnitPrice.setText(cur.getUnitPrice() + "");

            }
        });
    }


    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        MainFormController.navigateToMain(root);
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {

        for (TextField textField : new TextField[]{txtCode, txtDescription, txtQtyOnHand, txtUnitPrice}) {
            textField.clear();
            txtCode.requestFocus();
            tblItems.getSelectionModel().clearSelection();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (!isDataValid()) return;


        try {
            Item item = new Item(txtCode.getText(), txtDescription.getText(), Integer.parseInt(txtQtyOnHand.getText()), new BigDecimal(txtUnitPrice.getText()).setScale(2));


            if (btnSave.getText().equals("Save")) {
                if (ItemDataAccess.existsItem(item.getCode())) {
                    new Alert(Alert.AlertType.ERROR, "Item Code Already Exists").show();
                    txtCode.requestFocus();
                    txtCode.selectAll();
                    return;
                }

                ItemDataAccess.saveItems(item);
                tblItems.getItems().add(item);
                btnAddNewItem.fire();

            } else {

                ItemDataAccess.updateItems(item);
                ObservableList<Item> itemObservableList = tblItems.getItems();
                Item selectedItem = tblItems.getSelectionModel().getSelectedItem();
                itemObservableList.set(itemObservableList.indexOf(selectedItem), item);
                tblItems.refresh();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        Item selectedItem = tblItems.getSelectionModel().getSelectedItem();
        try {
            if(OrderDataAccess.existOrderByItemCode(selectedItem.getCode())){
                new Alert(Alert.AlertType.ERROR,"Item can not delete,Item already associate with Order").show();
            }

            ItemDataAccess.deleteItem(selectedItem.getCode());
            tblItems.getItems().remove(selectedItem);
            tblItems.refresh();

            if(tblItems.getItems().isEmpty()) btnAddNewItem.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed To delete item,try again");

        }


    }


    private boolean isDataValid() {
        String code = txtCode.getText().strip();
        String description = txtDescription.getText().strip();
        String qty = txtQtyOnHand.getText().strip();
        String unitPrice = txtUnitPrice.getText().strip();

        if (!code.matches("\\d{4,}")) {
            txtCode.requestFocus();
            txtCode.selectAll();
            return false;
        } else if (!description.matches("[A-Za-z0-9 ]{4,}")) {
            txtDescription.requestFocus();
            txtDescription.selectAll();
            return false;
        } else if (!qty.matches("\\d+") || Integer.parseInt(qty) <= 0) {
            txtQtyOnHand.requestFocus();
            txtQtyOnHand.selectAll();
            return false;
        } else if (!isPrice(unitPrice)) {
            txtUnitPrice.requestFocus();
            txtUnitPrice.selectAll();
            return false;

        }
        return true;
    }

    private boolean isPrice(String unitPrice) {
        try {
            double price = Double.parseDouble(unitPrice);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
