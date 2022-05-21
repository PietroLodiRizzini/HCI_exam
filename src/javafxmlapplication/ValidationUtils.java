/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author pietro
 */
public class ValidationUtils {
    public static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static String USERNAME_REGEX = "^[A-Za-z0-9_-]*$";
    public static String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!@#$%&*()-+=]).{8,20}$";
    
    public static String validateUsername(TextField usernameField, Text userNameError, HBox usernameLeftHBox, HBox usernameRightHBox) {
        usernameLeftHBox.setMinHeight(60);
        usernameRightHBox.setMinHeight(60);
        
        userNameError.setVisible(false);
        String username = usernameField.getText();
        if(AppInfo.getSingletonNavegacion().exitsNickName(username)) {
            userNameError.setText("This username already exists");
            userNameError.setVisible(true);
            return null;
        }
        else if(! username.matches(USERNAME_REGEX) || username.length() < 6 || username.length() > 15) {
            userNameError.setText("Username can contain only dashes, underscores, letters ond numbers and it must be between 6 and 15 characters");
            usernameLeftHBox.setMinHeight(80);
            usernameRightHBox.setMinHeight(80);
            userNameError.setVisible(true);
            return null;
        }
        return username;
    }
    
    public static String validateEmail(TextField emailField, Label emailError) {
        emailError.setVisible(false);   
        String email = emailField.getText();
        if(! email.matches(EMAIL_REGEX)) {
            emailError.setVisible(true);
            return null;
        }
        return email;
    }
    
    public static String validatePassword(TextField passwordField, Text passwordError, HBox passwordLeftHbox, HBox passwordRightHbox) {
        passwordLeftHbox.setMinHeight(60);
        passwordRightHbox.setMinHeight(60);
        
        passwordError.setVisible(false);
        String password = passwordField.getText();
        if(!password.matches(PASSWORD_REGEX)) {
            passwordError.setVisible(true);
            passwordLeftHbox.setMinHeight(100);
            passwordRightHbox.setMinHeight(100);
            return null;
        }
        return password;
    }
    
    public static LocalDate validateDOB(TextField yearField, TextField monthField, TextField dayField, Label dobError) {
        String year = yearField.getText();
        String month = monthField.getText();
        String day = dayField.getText();
        dobError.setVisible(false);
        LocalDate dob = null;
        try {
            dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        } catch(Exception e) {
            dobError.setVisible(true);
        }
        return dob;
    }
    
    public static Image validateImage(String imgPath, Label fileError) {
        fileError.setVisible(false);
        
        Image avatar = null;
        
        if(imgPath != null)
            try {
                avatar = new Image(new FileInputStream(imgPath));
            } catch (FileNotFoundException ex) {
                fileError.setVisible(true);
            }
        
        return avatar;
    }
    
}
