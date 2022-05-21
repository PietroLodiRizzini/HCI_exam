/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Navegacion;
import model.Session;

/**
 * FXML Controller class
 *
 * @author lucac
 */
public class FXMLHomeLoggedInController implements Initializable {

    @FXML
    private Text nameWelcome;
    private Stage primaryStage;
    private Scene primaryScene;
    @FXML
    private Text scoreLabel;
    @FXML
    private ProgressBar progressBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DoubleProperty doubleProperty = progressBar.progressProperty();
        StringProperty stringProperty = scoreLabel.textProperty();
        
        Bindings.bindBidirectional(stringProperty, doubleProperty, new StringConverter<Number> () {
            
            @Override
            public String toString(Number t) {
                double val = (t.doubleValue() * 100);
                DecimalFormat df = new DecimalFormat("###.##");
                return df.format(val) + "%";
            }

            @Override
            public Number fromString(String string) {
                try {
                    return Double.parseDouble(string.substring(0, string.indexOf("%"))) / 100;
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        });
        
        progressBar.setProgress(score());
    }

    void initLoggedHome(Stage stage) {
        nameWelcome.textProperty().setValue("Welcome " + AppInfo.getUser().getNickName() + "!");
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
    }

    @FXML
    private void randomQuestionClick(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLRandomQuestion.fxml"));
        BorderPane root = (BorderPane) myLoader.load();
        FXMLRandomQuestionController c = myLoader.<FXMLRandomQuestionController>getController();
        
        c.init(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Random Question");
        primaryStage.show();
    }

    @FXML
    private void viewProfileClick(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLProfileView.fxml"));
        HBox root = (HBox) myLoader.load();
        FXMLProfileViewController c = myLoader.<FXMLProfileViewController>getController();
        
        c.initMainWindow(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Random Question");
        primaryStage.show();
    }

    @FXML
    private void showResultsClick(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLShowResults.fxml"));
        VBox root = (VBox) myLoader.load();
        FXMLShowResultsController c = myLoader.<FXMLShowResultsController>getController();
        
        c.init(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Results");
        primaryStage.show();
    }

    @FXML
    private void logoutClick(ActionEvent event) throws IOException {
        AppInfo.setUser(null);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLFirstScreen.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        FXMLFirstScreenController signInContr = myLoader.<FXMLFirstScreenController>getController();
        
        signInContr.initMainWindow(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight()-38);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign-In");
        primaryStage.show();
    }
    
    private double score() {
        List<Session> sessions = AppInfo.getUser().getSessions();
        int faults = 0;
        int hits = 0;
        
        for(Session s : sessions) {
            faults += s.getFaults();
            hits += s.getHits();
        }
        
        if(faults == 0 && hits == 0)
            return 0.0;
        
        return ((double)hits / (faults + hits));
    }
}
