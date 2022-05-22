/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.awt.Paint;
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
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
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
    
    private MenuItem pin_info;
    private ListView<Poi> map_listview;
    @FXML
    private Pane mapPane;
    @FXML
    private ToggleButton drawPointBtn;

    @FXML
    private ToggleGroup drawToggle;
    @FXML
    private ToggleButton drawLineBtn;
    @FXML
    private ToggleButton drawArcBtn;
    
    private double circleCenterX;
    private double circleCenterY;
    
    @FXML
    private ToggleButton addTextBtn;
    @FXML
    private ColorPicker colorPicker;
    
    private Problem problem;
    int correctAnswer;

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
        
        this.problem = problem;
        
        questionTextArea.setText(problem.getText());
        
        List<Answer> answers = problem.getAnswers();
        //Collections.shuffle(answers);
        for(int i = 0; i < Math.min(answerRadios.size(), answers.size()); i++)
            answerRadios.get(i).setText(answers.get(i).getText());
        for(int i = 0; i < problem.getAnswers().size(); i++)
            if(problem.getAnswers().get(i).getValidity())
                correctAnswer = i;
                
    }

    @FXML
    private void confirmClick(ActionEvent event) {
        
        boolean correct = false;
        for(int i = 0; i < answerRadios.size(); i++) {
            if(answerToggleGroup.getSelectedToggle() == answerRadios.get(i) && correctAnswer == i) {
                correct = true;
            }
        }
        
        if(correct) {
            AppInfo.incrementHits();
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Answer is correct!");
            a.setOnCloseRequest(this::onAlertClose);
            a.show();
        } else {
            AppInfo.incrementFaults();
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Answer is not correct :(");
            a.setOnCloseRequest(this::onAlertClose);
            a.show();
        }
    }
    
    /**
     * When currect/incorrect answer alert is closed, a new random problem is loaded.
     * @param e 
     */
    private void onAlertClose(DialogEvent e) {
        this.initProblem(null);
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


    /**
     * Allows to draw a point if the point draw toggle button is selected.
     * @param event 
     */
    @FXML
    private void paneMouseClick(MouseEvent event) {        
        System.out.println("Pane click");
        Toggle selectedToggle = drawToggle.getSelectedToggle();
        
        double x = event.getX();
        double y = event.getY();
        
        if(selectedToggle == drawPointBtn) {
            System.out.println("draw point");
            Circle c = new Circle();

            c.setRadius(5.0);
            c.setCenterX(x);
            c.setCenterY(y);
            c.setStroke(Color.BLUE);
            c.setStrokeWidth(0.0);
            c.setOnMouseClicked(this::onShapeClick);
            mapPane.getChildren().add(c);
            drawToggle.selectToggle(null);
        }
    }
    
    /**
     * Allows to start drawing lines, circles and adding text.
     * @param event 
     */
    @FXML
    private void paneMousePressed(MouseEvent event) {
        Toggle selectedToggle = drawToggle.getSelectedToggle();
        
        double x = event.getX();
        double y = event.getY();
        
        if(selectedToggle == drawLineBtn) {
            
            Line linePainting = new Line(x, y, x, y);
            linePainting.setStrokeWidth(5.0);
            zoomGroup.getChildren().add(linePainting);
            
        } else if(selectedToggle == drawArcBtn) {
            
            Circle circle = new Circle();
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setFill(Color.rgb(0, 0, 0, 0.0));
            circle.setStroke(Color.rgb(0, 0, 0, 1.0));
            circleCenterX = x;
            circleCenterY = y;
            circle.setStrokeWidth(5.0);
            zoomGroup.getChildren().add(circle);
            
        } else if(selectedToggle == addTextBtn) {
            
            TextField textField = new TextField();
            zoomGroup.getChildren().add(textField);
            textField.setLayoutX(event.getX());
            textField.setLayoutY(event.getY());
            textField.requestFocus();
            
            // When the user is done editing the text, TextField gets converted to a Text:
            textField.setOnAction(e -> {
                Text label = new Text(textField.getText());
                label.setX(textField.getLayoutX());
                label.setY(textField.getLayoutY());
                label.setStyle("-fx-font-family: Gafatar;-fx-font-size: 20;");
                label.setOnMouseClicked(this::onShapeClick);
                zoomGroup.getChildren().add(label);
                zoomGroup.getChildren().remove(textField);
                e.consume();
            });
            
        }
    }
    
    /**
     * Allows color change on shape click.
     */
    private void onShapeClick(Event e) {
        // give priority to drawing functionalities
        if(drawToggle.getSelectedToggle() != null)
            return;
        
        Object source = e.getSource();
        Parent p = (Parent) source;
        
        if(source instanceof Shape) {
            Shape s = (Shape) source;
            s.setStroke(colorPicker.getValue());
            s.setFill(colorPicker.getValue());
        } else if(source instanceof Text) {
            Text t = (Text) source;
            t.setStroke(colorPicker.getValue());
            t.setFill(colorPicker.getValue());
        }
    }
    

    @FXML
    private void paneMouseDragged(MouseEvent event) {
        Toggle selectedToggle = drawToggle.getSelectedToggle();
        
        double x = event.getX();
        double y = event.getY();
        
        // element that is being painted
        Node p = zoomGroup.getChildren().get(zoomGroup.getChildren().size() - 1);
        
        if(selectedToggle == drawLineBtn) {
            Line linePainting = (Line) p;
            linePainting.setEndX(x);
            linePainting.setEndY(y);
            linePainting.setOnMouseClicked(this::onShapeClick);
            event.consume();
        } else if(selectedToggle == drawArcBtn) {
            Circle circle = (Circle) p;
            double newRadius = this.calculateDistanceBetweenPoints(circleCenterX, circleCenterY, x, y);
            circle.setOnMouseClicked(this::onShapeClick);
            circle.setRadius(newRadius);
        }
    }
    
    private double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {       
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    @FXML
    private void paneMouseReleased(MouseEvent event) {
        Toggle selectedToggle = drawToggle.getSelectedToggle();
        
        if(selectedToggle != drawPointBtn)
        drawToggle.selectToggle(null);
    }

    @FXML
    private void backClick(ActionEvent event) {
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
    
}
    