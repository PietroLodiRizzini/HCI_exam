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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class FXMLProfileViewController implements Initializable {

    @FXML
//    private Circle profileCircle;
    
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
    private void returnClick(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSignIn.fxml"));
        VBox root = (VBox) myLoader.load();
        FXMLFirstScreenController signInContr = myLoader.<FXMLFirstScreenController>getController();
        
    /**    signInContr.initSignIn(primaryStage); **/
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Random-question");
        primaryStage.show();
    }
    
}
