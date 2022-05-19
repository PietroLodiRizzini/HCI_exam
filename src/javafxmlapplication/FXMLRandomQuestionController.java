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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class FXMLRandomQuestionController implements Initializable {
    private Stage primaryStage;
    @FXML
    private TextArea questionTextArea;
    @FXML
    private RadioButton answer1Radio;
    @FXML
    private ToggleGroup answerToggleGroup;
    @FXML
    private RadioButton answer2Radio;
    @FXML
    private RadioButton answer3Radio;
    @FXML
    private RadioButton answer4Radio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void init(Stage stage) {
        primaryStage = stage;
        
        
    }

    @FXML
    private void confirmClick(ActionEvent event) {
    }
    
}
