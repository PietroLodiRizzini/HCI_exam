/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Answer;
import model.Problem;

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
    List<RadioButton> answerRadios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void init(Stage stage) {
        primaryStage = stage;
        
        answerRadios = new ArrayList<>(Arrays.asList(answer1Radio, answer2Radio, answer3Radio, answer4Radio));
        //answerRadios.addAll({});
        
        List<Problem> problems = AppInfo.getSingletonNavegacion().getProblems();
        if(problems.size() > 0) {
            int rand = (int) (Math.random() * problems.size());
            Problem extractedProblem = problems.get(rand);
            questionTextArea.setText(extractedProblem.getText());

            int i = 0;
            for(Answer a : extractedProblem.getAnswers()) {            
                answerRadios.get(i).setText(a.getText());
                i++;
            }
        }
        
    }

    @FXML
    private void confirmClick(ActionEvent event) {
    }
    
}
