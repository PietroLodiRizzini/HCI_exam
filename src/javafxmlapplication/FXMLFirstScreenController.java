/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author lucac
 */
public class FXMLFirstScreenController implements Initializable {

    @FXML
    private Button enterButton;
    @FXML
    private Text loginError;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    
    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
    }    
    
    public void initMainWindow(Stage stage){
            primaryStage = stage;
    
    }

    @FXML
    private void handleEnterButton(ActionEvent event) throws IOException {
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
        
        if(AppInfo.DEBUG) {
            userNameExists = true;
            username = AppInfo.DEBUG_USERNAME;
            password = AppInfo.DEBUG_PASSWORD;
        }

        if(userNameExists) {
            User u = n.loginUser(username, password);
            if(u == null) {
                loginError.setText("Username or password\nare incorrect");
                loginError.setVisible(true);
            }
            //else -> todoCorrect
            else { 
                AppInfo.setUser(u);                
                
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
                Parent root = myLoader.load();
                FXMLHomeLoggedInController loggedHomeController = myLoader.getController();
                
                loggedHomeController.initLoggedHome(primaryStage);
                
                //We create the scene foe win1
                Scene scene = new Scene(root);
                //we assign new scene to current stage/window
                primaryStage.setScene(scene);
                primaryStage.setTitle("Logged");
                primaryStage.show();
            }
        } else {
            loginError.setText("Username does not exist");
            loginError.setVisible(true);
        }
    }

    @FXML
    private void signInClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSignIn.fxml"));
        
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Sign-in");
        s.setScene(scene);
        s.setResizable(false);
        s.initModality(Modality.APPLICATION_MODAL);
        s.show();
    }
    
}
