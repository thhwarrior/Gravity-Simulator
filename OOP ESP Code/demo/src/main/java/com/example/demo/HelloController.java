package com.example.demo;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Stop;

public class HelloController implements Initializable {

    // Media related fields
    String localDir = System.getProperty("user.dir");
    String mediaResource = localDir + "\\src\\main\\resources\\com\\example\\demo";
    Media hoverSound = new Media(new File(mediaResource + "\\MouseHover.wav").toURI().toString());
    Media clickSound = new Media(new File(mediaResource + "\\MouseClick.wav").toURI().toString());
    Media bgVideo = new Media(new File(mediaResource + "\\bgVideo.mp4").toURI().toString());
    Media bgMusic = new Media(new File(mediaResource + "\\bgMusic.mp3").toURI().toString());
    MediaPlayer hoverSoundPlayer = new MediaPlayer(hoverSound);
    MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);
    MediaPlayer bgVideoPlayer = new MediaPlayer(bgVideo);
    MediaPlayer bgMusicPlayer = new MediaPlayer(bgMusic);

    // End

    // FXML fields
    @FXML private MediaView mediaView;

    @FXML private Button aboutBtn;

    @FXML private Button startBtn;

    @FXML private TextField username;

    @FXML private Button confirmUsername;
    // End

    // Other fields
    public static String user;
    static boolean firstLoop = true;
    public static Stage titleStage;
    // End


    // TitleScreen Methods
    @FXML void click(ActionEvent event) {
        clickSoundPlayer.play();
    }

    @FXML void enter(MouseEvent event) {
        hoverSoundPlayer.play();
    }

    @FXML void exit(MouseEvent event) {
        hoverSoundPlayer.stop();
        clickSoundPlayer.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (firstLoop) {
            mediaView.setMediaPlayer(bgVideoPlayer);
            bgVideoPlayer.play();
            bgVideoPlayer.setCycleCount(-1);
            bgMusicPlayer.play();
            bgMusicPlayer.setCycleCount(-1);
            firstLoop = false;
        }
    }

    @FXML void ExitProgram(ActionEvent event){System.exit(0);}

    @FXML void AboutUs(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AboutUS.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("About US");
        stage.setHeight(300);
        stage.setWidth(350);
        stage.showAndWait();
    }

    @FXML void Start(ActionEvent event) throws IOException {
        bgMusicPlayer.stop();
        bgVideoPlayer.stop();
        Parent root = FXMLLoader.load(getClass().getResource("usernameReceiver.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Enter Username");
        stage.setHeight(150);
        stage.setWidth(300);
        stage.showAndWait();
    }
    // End

    @FXML void Confirm(ActionEvent event) throws IOException {
        if (username.getText().isBlank()) {username.setText("This cannot be blank");}
        else {
            user = username.getText();
            titleStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("Simulation.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gravity Simulator");
            stage.setWidth(Utility.WindowSize.X);
            stage.setHeight(Utility.WindowSize.Y + 25);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    try {
                        DataBase.DB_Info_Insert(HelloController.user, (SimulationController.planets_num + 5), ("From" + Simulation.startTime + " to " + LocalTime.now().toString()));
                        DataBase.DB_Create_Table();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


}

