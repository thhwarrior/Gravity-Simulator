package com.example.demo;

import java.sql.SQLException;
import java.time.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML private Canvas canvas;
    @FXML private TextField n_in;
    @FXML private TextField r_in;
    @FXML private TextField m_in;
    @FXML private ColorPicker c_in;
    @FXML private Slider vx_in;
    @FXML private Slider vy_in;
    @FXML private Button confirmButton;
    @FXML private Label VelXLabel;
    @FXML private Label VelYLabel;


    public GraphicsContext gc;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public String name;
    public int radius;
    public int mass;
    public int vx;
    public int vy;
    public Color color;
    public static int planets_num;

    static boolean firstLoop = true;

    public void openForm(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("form.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("New Planet");
        stage.showAndWait();
    }

    @FXML
    public void ChangeSliderValue(){
        vx_in.valueProperty().addListener((observable, oldValue, newValue) -> VelXLabel.setText("Velocity X: " + newValue.intValue()));
        vy_in.valueProperty().addListener((observable, oldValue, newValue) -> VelYLabel.setText("Velocity Y: " + newValue.intValue()));
    }

    @FXML
    void Confirm(ActionEvent event) throws SQLException {

        boolean nameValid = false;
        boolean radiusValid = false;
        boolean massValid = false;

        if (n_in.getText().isBlank()){n_in.setText("Please enter valid name"); }
        else {name = n_in.getText(); nameValid = true;}

        if (r_in.getText().isBlank()){r_in.setText("Please enter valid radius");}
        else if (!isPositiveInteger(r_in)) {r_in.setText("Please enter valid radius");}
        else{radius = Integer.parseInt(r_in.getText()); radiusValid = true;}

        if (m_in.getText() == ""){m_in.setText("Please enter valid mass");}
        else if (!isPositiveInteger(m_in)) {m_in.setText("Please enter valid mass");}
        else{mass = Integer.parseInt(m_in.getText()); massValid = true;}

        if(nameValid && radiusValid && massValid){
            color = c_in.getValue();
            vx = (int)vx_in.getValue();
            vy = (int)vy_in.getValue();
            planets_num += 1;

            Simulation.Mass = mass;
            Simulation.Radius = radius;
            Simulation.VelX = vx;
            Simulation.VelY = vy;
            Simulation.color = color;
            Simulation.addPlanetMode = true;

            Stage formStage = (Stage) confirmButton.getScene().getWindow();
            formStage.close();
            //DataBase.DB_Detail_Insert("Created Planet: " + name);
            DataBase.DB_Detail_Insert("Created Planet: " + name + ", " + "Radius: " + radius + ", " + "Mass: " + mass + ", " + "Color: " + color + ", " + "Initial Coordinates: (" + Simulation.posX + ", " + Simulation.posY + ")" + ", " + "InitialVelocity: " + (int)Math.sqrt(vx*vx + vy*vy));
        }

    }

    @FXML
    void Save(ActionEvent event) throws SQLException {
        //creates new table for the log to go into
//        DataBase.DB_Info_Insert(HelloController.user, Simulation.planets.size(), ("From" + Simulation.startTime + " to " + LocalTime.now().toString()));
//        DataBase.DB_Create_Table();
    }

    @FXML
    void Help(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Help.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.setHeight(220);
        stage.setWidth(420);
        stage.showAndWait();
    }

    @FXML
    void ExitProgram(ActionEvent event) throws SQLException{
//        DataBase.DB_Info_Insert(HelloController.user, Simulation.planets.size(), ("From" + Simulation.startTime + " to " + LocalTime.now().toString()));
        DataBase.DB_Info_Insert(HelloController.user, (planets_num + 5), ("From" + Simulation.startTime + " to " + LocalTime.now().toString()));
        DataBase.DB_Create_Table();
        System.exit(0);
    }

    @FXML
    void AboutUs(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AboutUS.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("About US");
        stage.setHeight(300);
        stage.setWidth(350);
        stage.showAndWait();
    }

    boolean isPositiveInteger(TextField t){
        try {
             int i = Integer.parseInt(t.getText());
             return i>0;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (firstLoop){
            firstLoop = false;
            gc = canvas.getGraphicsContext2D();
            Simulation.Start(gc, canvas);
        }
    }
}
