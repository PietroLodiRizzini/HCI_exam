/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucac
 */
public class FXMLHomeLoggedInController implements Initializable {

    @FXML
    private Text nameWelcome;
    private Stage primaryStage;
    private Scene primaryScene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void initLoggedHome(Stage stage, String username) {
        nameWelcome.textProperty().setValue("Welcome "+username+"!");
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
    }
    
}
