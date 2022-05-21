/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Session;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class FXMLShowResultsController implements Initializable {
    private Stage scene;
    @FXML
    private DatePicker fromDP;
    @FXML
    private DatePicker toDP;
    private ListView<Session> resultsListView;
    
    private ObservableList<Session> sessions;
    @FXML
    private TableView<Session> table;
    @FXML
    private TableColumn<Session, LocalDateTime> dateCol;
    @FXML
    private TableColumn<Session, Integer> hitsCol;
    @FXML
    private TableColumn<Session, Integer> faultsCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void init(Stage scene) {
        this.scene = scene;
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusMonths(1);
        
        fromDP.setValue(from);
        toDP.setValue(to);
        
        sessions = FXCollections.observableArrayList();
        table.setItems(sessions);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        hitsCol.setCellValueFactory(new PropertyValueFactory<>("hits"));
        faultsCol.setCellValueFactory(new PropertyValueFactory<>("faults"));
    }

    @FXML
    private void filterClick(ActionEvent event) {
        sessions.clear();
        
        for(Session s : AppInfo.getUser().getSessions()) {
            if (s.getLocalDate().isAfter(fromDP.getValue())
                    && s.getLocalDate().isBefore(toDP.getValue()))
                sessions.add(s);
        }
    }
    
}
