package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Planet {
    public int radius;
    public int mass;
    public Vector Position;
    public Vector Velocity;
    
    public Color color;

    public Planet(int radius, int mass, Vector position, Vector velocity, Color color)
    {
        this.color = color;
        this.radius = radius;
        this.mass = mass;
        Position = position;
        Velocity = velocity;
        Simulation.planets.add(this);
    }

    public void UpdatePosition()
    {
        Vector velocity = getVelocity();
        Vector vt = velocity.MultiplyVectorWithScalar(Physics.t);
        Vector NewPosition = Position.AddVector(vt);
        Line line = new Line(Position, NewPosition);
        Position = NewPosition;
    }

    public Vector getVelocity()
    {
        Vector Acceleration = getAcceleration();
        Vector at = Acceleration.MultiplyVectorWithScalar(Physics.t);
        Velocity = Velocity.AddVector(at);
        return Velocity;
    }

    public Vector getAcceleration()
    {
        Vector F = getForce();
        return F.DivideVectorWithScalar(mass);
    }

    public Vector getForce()
    {
        double F;
        double Fx = 0;
        double Fy = 0;
        Vector distance;
        double distanceX;
        double distanceY;
        double r;

        for (Planet planet : Simulation.planets) {
            if (planet != this)
            {
                distance = Vector.getDisplacement(Position, planet.Position);
                distanceX = distance.X;
                distanceY = distance.Y;
                r = Vector.getMagnitude(distance);

                if (r < (radius + planet.radius))
                {
                    if (mass > planet.mass){
                        Simulation.listOfPlanetsToBeDeleted.add(planet);
                        mass += planet.mass;
                    } else if (mass < planet.mass) {
                        Simulation.listOfPlanetsToBeDeleted.add(this);
                        break;
                    } else{
                        Simulation.listOfPlanetsToBeDeleted.add(planet);
                        Simulation.listOfPlanetsToBeDeleted.add(this);
                    }
                }

                F = ((Physics.G * mass * planet.mass) / (r * r));
                Fx += F * (distanceX / r);
                Fy += F * (distanceY / r);
            }
        }
        return new Vector(Fx, Fy);
    }

    public void Display(GraphicsContext gc){
        Vector SimulationPosition = Utility.ConvertToSimulationCoordinates(Position);

        double MinX = SimulationPosition.X-radius;
        double MinY = SimulationPosition.Y-radius;

        gc.setFill(color);
        gc.setStroke(color);
        gc.strokeOval(MinX, MinY, radius * 2, radius * 2);
        gc.fillOval(MinX, MinY, radius * 2, radius * 2);
    }
}
