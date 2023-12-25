package org.lk.pos.controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.lk.pos.AppInitializer;

import java.io.IOException;


public class MainFormController {
    public javafx.scene.image.ImageView imgOrder;
    public javafx.scene.image.ImageView imgViewOrders;
    public javafx.scene.image.ImageView imgViewOrders1;
    public Label lblMenu;
    public AnchorPane root;
    public javafx.scene.image.ImageView imgCustomer;
    public javafx.scene.image.ImageView imgItem;

    @FXML
    public void navigate(MouseEvent mouseEvent) throws IOException {
        
        if(mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();
            Parent root=null;
            switch (icon.getId()){
                case "imgCustomer":
                    root= FXMLLoader.load(this.getClass().getResource("/view/ManageCustomerForm.fxml"));
                    break;
                case "imgItem":
                    root=FXMLLoader.load(this.getClass().getResource("/view/ManageItemForm.fxml"));
                    break;
                case "imgOrder":
                    root=FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));
                    break;
                case "imgViewOrders":
                    root=FXMLLoader.load(this.getClass().getResource("/view/SearchOrderForm.fxml"));
                    break;
            }
            if(root!=null){
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setResizable(true);
                primaryStage.setScene(subScene);
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
                primaryStage.setOnCloseRequest(Event::consume);

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

                Platform.runLater(()->primaryStage.setResizable(false));


            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
    }

    public static void navigateToMain(Node rootNode) throws IOException {
        Parent root = FXMLLoader.load(AppInitializer.class.getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) rootNode.getScene().getWindow();
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(null);
        Platform.runLater(()->primaryStage.setResizable(false));
    }
}
