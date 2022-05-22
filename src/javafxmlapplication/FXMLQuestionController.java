/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Answer;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author pietro
 */
public class FXMLQuestionController implements Initializable {
    private Group zoomGroup;
    private Stage primaryStage;
    
    private final HashMap<String, Poi> hm = new HashMap<>();
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
    @FXML
    private Slider zoom_slider;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    @FXML
    private Button markPointBtn;
    @FXML
    private ListView<Poi> map_listview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * init random problem controller; if problem is null, a problem is extracted randomly.
     * @param stage
     * @param problem 
     */
    void init(Stage stage, Problem problem) {
        primaryStage = stage;
        
        initProblem(problem);
        initData();
        
        // init zoom
        
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
    }
    
    @FXML
    void listClicked(MouseEvent event) {
        
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    private void initData() {
        hm.put("2F", new Poi("2F", "Edificion del DSIC", 325, 225));
        //hm.put("Agora", new Poi("Agora", "Agora", 600, 360));
        map_listview.getItems().add(hm.get("2F"));
        //map_listview.getItems().add(hm.get("Agora"));
    }
    
    /**
     * Init problem related fields with data from problem arg or an extracted problem if the arg is null.
     * 
     * @param problem 
     */
    private void initProblem(Problem problem) {
        answerRadios = new ArrayList<>(Arrays.asList(answer1Radio, answer2Radio, answer3Radio, answer4Radio));
        //answerRadios.addAll({});
        
        if(problem == null) {
            List<Problem> problems = AppInfo.getSingletonNavegacion().getProblems();
            if(! problems.isEmpty()) {
                int rand = (int) (Math.random() * problems.size());
                problem = problems.get(rand);
            }
        }
        
        questionTextArea.setText(problem.getText());
        
        List<Answer> answers = problem.getAnswers();
        //Collections.shuffle(answers);
        for(int i = 0; i < Math.min(answerRadios.size(), answers.size()); i++)
            answerRadios.get(i).setText(answers.get(i).getText());
    }

    @FXML
    private void confirmClick(ActionEvent event) {
    }

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

    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
    }

    @FXML
    private void markPointClick(ActionEvent event) {
    }

    @FXML
    private void paneMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        
        Poi p = new Poi();
        p.setPosition(new Point2D(x, y));
        p.setCode("11");
        hm.put("new", p);
        System.out.println("x: " + x + "y: " + y);
        map_listview.getItems().add(hm.get("new"));
    }
    
}
