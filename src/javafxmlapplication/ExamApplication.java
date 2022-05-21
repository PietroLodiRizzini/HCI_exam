/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.time.LocalDate;
import java.time.Month;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 *
 * @author jose
 */
public class ExamApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("ExamApp");
        AppInfo.initDebug();
        
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("FXMLFirstScreen.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- scene creation provideng the root node of the scene
        Scene scene = new Scene(root);
        
        FXMLFirstScreenController mainWinController = loader.<FXMLFirstScreenController>getController();
        //Store the current stage as mainStage
         mainWinController.initMainWindow(stage);
      
        //======================================================================
        // 4- the scene is assigned to the stage
        
        stage.setScene(scene);
        //stage configuration
        stage.setTitle("ExamApp");
        stage.setResizable(true); 
        //Show the stage/window
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
