/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class RegisterController implements Initializable {

    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private Label userNameError;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailError;
    @FXML
    private TextField passwordField;
    @FXML
    private Label passwordError;
    @FXML
    private TextField monthField;
    @FXML
    private TextField dayField;
    @FXML
    private TextField yearField;
    @FXML
    private Label dobError;
    @FXML
    private Button fileButton;
    @FXML
    private Label fileError;
    @FXML
    private Label fileLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registerClicked(ActionEvent event) {
    }

    @FXML
    private void pickFileClicked(ActionEvent event) {
    }
    
}
