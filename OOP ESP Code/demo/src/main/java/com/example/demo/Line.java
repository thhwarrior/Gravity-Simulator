package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Line
{
    public Vector Point1;
    public Vector Point2;
    public int Duration;
    public double length;
    public Vector direction;

    public Line(Vector point1, Vector point2)
    {
        Point1 = point1;
        Point2 = point2;
        Duration = 120;
        direction = Vector.getDisplacement(point1, point2);
        length = Vector.getMagnitude(direction);
        Simulation.lines.add(this);
    }

    public void Show(GraphicsContext gc){
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        Vector from = Utility.ConvertToSimulationCoordinates(Point1);
        Vector to = Utility.ConvertToSimulationCoordinates(Point2);
        gc.strokeLine(from.X, from.Y, to.X, to.Y);
        Duration--;
        if (Duration==0){
            Simulation.listOfLinesToBeDeleted.add(this);
        }
    }
}
