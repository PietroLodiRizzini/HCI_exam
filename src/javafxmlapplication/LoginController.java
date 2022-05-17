/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class LoginController implements Initializable {

    @FXML
    private Label loginError;
    @FXML
    private Button login;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginClick(ActionEvent event) {
        Navegacion n = null;
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        try {
            n = Navegacion.getSingletonNavegacion();           
        } catch (NavegacionDAOException e) {
            e.printStackTrace();
            return;
        }
        
        boolean userNameExists = n.exitsNickName(username);

        if(userNameExists) {
            User u = n.loginUser(username, password);
            if(u == null) {
                loginError.setText("Username or password are incorrect");
                loginError.setVisible(true);
            }
            else {
                ExamApplication.setUser(u);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("You are logged in.");
                a.show();
                
                
            }
        } else {
            loginError.setText("Username does not exist");
            loginError.setVisible(true);
        }
    }
    
}
