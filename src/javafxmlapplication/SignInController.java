/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class SignInController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private Text userNameError;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailError;
    @FXML
    private TextField passwordField;
    @FXML
    private Text passwordError;
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
    
    
    private String imgPath;
    @FXML
    private HBox passwordLeftHbox;
    @FXML
    private HBox passwordRightHbox;
    @FXML
    private HBox usernameLeftHBox;
    @FXML
    private HBox usernameRightHBox;
    @FXML
    private Button signInButton;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signInClick(ActionEvent event) throws IOException {
        Navegacion n = AppInfo.getSingletonNavegacion();
        
        String username = ValidationUtils.validateUsername(usernameField, userNameError, usernameLeftHBox, usernameRightHBox);
        String email = ValidationUtils.validateEmail(emailField, emailError);
        String password = ValidationUtils.validatePassword(passwordField, passwordError, passwordLeftHbox, passwordRightHbox);
        LocalDate dob = ValidationUtils.validateDOB(yearField, monthField, dayField, dobError);
        Image avatar = ValidationUtils.validateImage(imgPath, fileError);
        
        if(username == null || email == null || password == null || dob == null)
            return;
        
        if(imgPath != null && avatar == null)
            return;

        User u;
        
        try {
            if(imgPath == null) {
                u = n.registerUser(username, email, password, dob);
            } else {
                u = n.registerUser(username, email, password, avatar, dob);
            }
        } catch(NavegacionDAOException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }
        
        AppInfo.setUser(u);
        
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        FXMLHomeLoggedInController c = myLoader.<FXMLHomeLoggedInController>getController();
        
        c.initLoggedHome(primaryStage);
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }    

    @FXML
    private void pickFileClicked(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("PNG images", "*.png"));
        
        File f = fc.showOpenDialog(null);
        fileLabel.setText("File selected: " + f.getName());
        fileLabel.setVisible(true);
        imgPath = f.getAbsolutePath();
    }


    void initSignIn(Stage stage) {       
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLFirstScreen.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        FXMLFirstScreenController c = myLoader.<FXMLFirstScreenController>getController();
        
        c.initMainWindow(primaryStage);
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Log-in");
        primaryStage.show();
    }
    
}
