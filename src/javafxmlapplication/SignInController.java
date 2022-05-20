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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signInClick(ActionEvent event) throws IOException {
        Navegacion n;
        try {
            n = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException ex) {
            ex.printStackTrace();
            return;
        }
        
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String year = yearField.getText();
        String month = monthField.getText();
        String day = dayField.getText();
        LocalDate dob = null;
        Image avatar = null;
        
        userNameError.setVisible(false);
        emailError.setVisible(false);
        passwordError.setVisible(false);
        dobError.setVisible(false);
        fileError.setVisible(false);
        usernameLeftHBox.setMinHeight(60);
        usernameRightHBox.setMinHeight(60);
        passwordLeftHbox.setMinHeight(60);
        passwordRightHbox.setMinHeight(60);
        
        if(imgPath != null) 
            try {
                avatar = new Image(new FileInputStream(imgPath));
            } catch (FileNotFoundException ex) {
                fileError.setVisible(true);
            }
        
        if(n.exitsNickName(username)) {
            userNameError.setText("This username already exists");
            userNameError.setVisible(true);
        }
        else if(! username.matches("^[A-Za-z0-9_-]*$") || username.length() < 6 || username.length() > 15) {
            userNameError.setText("Username can contain only dashes, underscores, letters ond numbers and it must be between 6 and 15 characters");
            usernameLeftHBox.setMinHeight(80);
            usernameRightHBox.setMinHeight(80);
            userNameError.setVisible(true);
        }
        
        if(! email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            emailError.setVisible(true);
        }
        
        if(!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!@#$%&*()-+=]).{8,20}$")) {
            passwordError.setVisible(true);
            passwordLeftHbox.setMinHeight(100);
            passwordRightHbox.setMinHeight(100);
        }
        
        try {
            dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        } catch(Exception e) {
            dobError.setVisible(true);
        }
        
        if(userNameError.isVisible() || emailError.isVisible() || passwordError.isVisible() || dobError.isVisible() || fileError.isVisible())
            return;
        
        User u;
        
        try {
            if(avatar == null) {
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
        
        ExamApplication.setUser(u);
        
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        FXMLHomeLoggedInController signInContr = myLoader.<FXMLHomeLoggedInController>getController();
        
        signInContr.initLoggedHome(primaryStage);
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
        
        
        /*
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
        BorderPane root = (BorderPane) myLoader.load();
        FXMLHomeLoggedInController loggedHomeController = myLoader.<FXMLHomeLoggedInController>getController();

        AppInfo.setUser(u);
        Scene scene = new Scene(root);
        
        Stage s = (Stage)((Node) event.getSource()).getScene().getWindow();
        s.close(); */
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
    
}
