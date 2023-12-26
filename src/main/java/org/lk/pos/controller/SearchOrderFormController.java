package org.lk.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.lk.pos.AppInitializer;

import java.io.IOException;

public class SearchOrderFormController {
    public AnchorPane root;
    public TextField txtSearch;
    public TableView tblOrders;
    public ImageView imgHome;

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        MainFormController.navigateToMain(root);

    }
}
