/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

import java.time.LocalDate;
import java.time.Month;
import javafx.application.Application;
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
public class ExamApp extends Application {
    private static User user;
    
    @Override
    public void start(Stage stage) throws Exception {
        Navegacion n = Navegacion.getSingletonNavegacion();
        //n.registerUser("abc", "a@b.com", "abcA1234_", LocalDate.of(2000, Month.MARCH, 1));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Exam app");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void setUser(User u){
        user = u;
    }
    
    public static User getUser() {
        return user;
    }
    
}
