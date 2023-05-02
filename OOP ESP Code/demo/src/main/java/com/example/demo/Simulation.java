package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.ArrayList;

public class Simulation {
    public static String startTime = LocalTime.now().toString();
    public static ArrayList<Planet> planets = new ArrayList<Planet>();
    public static ArrayList<Line> lines = new ArrayList<Line>();
    public static ArrayList<Planet> listOfPlanetsToBeDeleted = new ArrayList<Planet>();
    public static ArrayList<Line> listOfLinesToBeDeleted = new ArrayList<Line>();

    static GraphicsContext gc;
    static Canvas canvas;
    static boolean addPlanetMode = false;

    public static int Mass;
    public static int Radius;

    public static Color color;
    public static int VelX;
    public static int VelY;

    static double posX = -100;
    static double posY = -100;

    public static void Start(GraphicsContext graphicsContext, Canvas c){
        canvas = c;
        gc = graphicsContext;
        Planet mainStar = new Planet(30,10000,Vector.Zero,Vector.Zero,Color.YELLOW);
        Planet testPlanet1 = new Planet(5,10,new Vector(125,0),new Vector(-10,75),Color.WHITE);
        Planet testPlanet2 = new Planet(5,10,new Vector(0,125),new Vector(-75,-10),Color.RED);
        Planet testPlanet3 = new Planet(5,10,new Vector(-125,0),new Vector(10,-75),Color.GREEN);
        Planet testPlanet4 = new Planet(5,10,new Vector(0,-125),new Vector(75,10),Color.BLUE);

        AnimationTimer simulationLoop = new AnimationTimer() {
            @Override
            public void handle(long time) {
                Simulate();
            }
        };
        simulationLoop.start();
    }

    public static void Simulate(){
        drawBackground();

        if (addPlanetMode){
            canvas.setOnMouseMoved(mouseEvent -> {
                posX = mouseEvent.getX();
                posY = mouseEvent.getY();
            });
            DrawText();
            canvas.setOnMouseClicked(mouseEvent -> {
                posX = mouseEvent.getX();
                posY = mouseEvent.getY();
                Vector Position = Utility.ConvertToCartesianCoordinates(new Vector(posX,posY));
                if (addPlanetMode) {new Planet(Radius,Mass,Position,new Vector(VelX,VelY),color);}
                addPlanetMode = false;
                posX = -100;
                posY = -100;
            });
        }

        DisplayPaths();
        DisplayPlanets();
    }

    static void drawBackground(){
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,Utility.WindowSize.X,Utility.WindowSize.Y);
    }
    static void DisplayPlanets(){
        for (Planet planet: planets) {
            planet.Display(gc);
            planet.UpdatePosition();
        }
        for (Planet planet: listOfPlanetsToBeDeleted) {
            planets.remove(planet);
        }
        listOfPlanetsToBeDeleted.clear();
    }
    static void DisplayPaths(){
        for (Line line: lines) {
            line.Show(gc);
        }
        for (Line line: listOfLinesToBeDeleted) {
            lines.remove(line);
        }
        listOfLinesToBeDeleted.clear();
    }
    static void DrawText(){
        gc.setFont(new Font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Click To Add", posX, posY-10);
    }
}
