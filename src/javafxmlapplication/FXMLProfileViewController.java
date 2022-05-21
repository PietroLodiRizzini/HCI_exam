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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author megan
 */
public class FXMLProfileViewController implements Initializable {

    
    private Stage primaryStage;
    @FXML
    private ImageView profilePicture;
    private Image newImage;
    @FXML
    private HBox usernameLeftHBox;
    @FXML
    private HBox passwordLeftHbox;
    @FXML
    private HBox usernameRightHBox;
    @FXML
    private TextField usernameField;
    @FXML
    private Text userNameError;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailError;
    @FXML
    private HBox passwordRightHbox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text passwordError;
    @FXML
    private TextField dayField;
    @FXML
    private TextField monthField;
    @FXML
    private TextField yearField;
    @FXML
    private Label dobError;
    @FXML
    private Label fileError;
    
    private String imgPath;
    @FXML
    private Button cancelButon;
    @FXML
    private Button fileButton11;
    @FXML
    private Button homeButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void initMainWindow(Stage stage){
            primaryStage = stage;
            updateProfilePicture();
            
            updateFields();
    }

    @FXML
    private void backToMainClick() {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
        AnchorPane root = null;
        try {
            root = (AnchorPane) myLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        FXMLHomeLoggedInController c = myLoader.<FXMLHomeLoggedInController>getController();
        
        c.initLoggedHome(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your profile");
        primaryStage.show();
    }
    
    private void updateProfilePicture() {
        Image avatar = AppInfo.getUser().getAvatar();
        profilePicture.imageProperty().setValue(avatar);
    }
    
    private void updateFields() {
        User u = AppInfo.getUser();
        usernameField.setText(u.getNickName());
        emailField.setText(u.getEmail());
        passwordField.setText(u.getPassword());
        dayField.setText(u.getBirthdate().getDayOfMonth() + "");
        monthField.setText(u.getBirthdate().getMonthValue() + "");
        yearField.setText(u.getBirthdate().getYear() + "");
    }

    @FXML
    private void changePictureClick(MouseEvent event) throws FileNotFoundException, NavegacionDAOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
        File f = fc.showOpenDialog(null);
        
        if(f == null)
            return;
        
        imgPath = f.getAbsolutePath();
        Image avatar = new Image(new FileInputStream(imgPath));
        newImage = avatar;
        
        profilePicture.imageProperty().setValue(avatar);
    }


    @FXML
    private void updateProfileClick(ActionEvent event) throws NavegacionDAOException {
        String email = ValidationUtils.validateEmail(emailField, emailError);
        String password = ValidationUtils.validatePassword(passwordField, passwordError, passwordLeftHbox, passwordRightHbox);
        LocalDate dob = ValidationUtils.validateDOB(yearField, monthField, dayField, dobError);
        Image avatar = ValidationUtils.validateImage(imgPath, fileError);
        
        if(email == null || password == null || dob == null)
            return;
        
        if(imgPath != null && avatar == null) {
            imgPath = null;
            updateProfilePicture();
            return;
        }
            

        User u = AppInfo.getUser();
        
        try {
            u.setEmail(email);
            u.setPassword(password);
            u.setBirthdate(dob);
            
            if(imgPath != null)
                u.setAvatar(avatar);
            
        } catch(NavegacionDAOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
        }
        
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Success");
        a.setHeaderText("Profile updated successfully!");
        a.show();
    }

    @FXML
    private void handleHomeButton(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLHomeLoggedIn.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        FXMLHomeLoggedInController c = myLoader.<FXMLHomeLoggedInController>getController();
        
        c.initLoggedHome(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }
    
}
