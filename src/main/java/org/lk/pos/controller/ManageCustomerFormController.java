package org.lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.lk.pos.db.CustomerDataAccess;
import org.lk.pos.db.OrderDataAccess;
import org.lk.pos.tm.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class ManageCustomerFormController {
    public AnchorPane root;
    public ImageView imgHome;
    public JFXButton btnAddNewCustomer;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<Customer> tblCustomer;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;


    public void initialize() {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        txtCustomerId.setEditable(false);
        btnDelete.setDisable(true);
        btnSave.setDefaultButton(true);
        btnAddNewCustomer.fire();

        try {
            tblCustomer.getItems().addAll(CustomerDataAccess.getAlLCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed To Load Customers").show();
        }

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((ov, prev, cur) -> {
            if (cur != null) {
                btnSave.setText("UPDATE");
                btnDelete.setDisable(false);
                txtCustomerId.setText(cur.getId());
                txtCustomerName.setText(cur.getName());
                txtCustomerAddress.setText(cur.getAddress());
            } else {
                btnSave.setText("Save");
                btnDelete.setDisable(true);
            }

        });


    }


    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        MainFormController.navigateToMain(root);
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) throws IOException {

        for (TextField textField : new TextField[]{txtCustomerId, txtCustomerName, txtCustomerAddress}) {
            textField.clear();
            tblCustomer.getSelectionModel().clearSelection();
            txtCustomerName.requestFocus();
        }

        try {
            String lastCustomerId = CustomerDataAccess.getLastCustomerId();
            if (lastCustomerId == null) {
                txtCustomerId.setText("C001");
            } else {
                int newId = Integer.parseInt(lastCustomerId.substring(1)) + 1;
                txtCustomerId.setText(String.format("C%03d", newId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Establish the Database Connection,try Again").show();
            navigateToHome(null);
        }

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        try {
            if (!isDataValid()) return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Customer customer = new Customer(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerAddress.getText());

        try {

            if (btnSave.getText().equals("Save")) {
                CustomerDataAccess.saveCustomer(customer);
                tblCustomer.getItems().add(customer);
            } else {

                CustomerDataAccess.updateCustomer(customer);
                ObservableList<Customer> customerList = tblCustomer.getItems();
                Customer selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
                customerList.set(customerList.indexOf(selectedItem), customer);
                tblCustomer.refresh();


            }


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Save Customer,try Again");
            throw new RuntimeException(e);
        }

        btnAddNewCustomer.fire();


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        try {
            if (OrderDataAccess.existsOrderByCustomerId(txtCustomerId.getText())) {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete:this customer is already associated with an order").show();

            } else {
                CustomerDataAccess.deleteCustomer(txtCustomerId.getText());
                ObservableList<Customer> customerList = tblCustomer.getItems();
                Customer selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
                customerList.remove(selectedCustomer);
                if(customerList.isEmpty()) btnAddNewCustomer.fire();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private boolean isDataValid() {
        String name = txtCustomerName.getText().strip();
        String address = txtCustomerAddress.getText().strip();
        if (!name.matches("[A-Za-z ]{2,}")) {
            txtCustomerName.requestFocus();
            txtCustomerName.selectAll();
            return false;
        } else if (address.length() < 3) {
            txtCustomerAddress.requestFocus();
            txtCustomerAddress.selectAll();
            return false;
        }
        return true;
    }
}
