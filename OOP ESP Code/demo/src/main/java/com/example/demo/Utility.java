package com.example.demo;

public class Utility {
    public static Vector WindowSize = new Vector(1200,600);
    public static Vector ConvertToSimulationCoordinates(Vector P)
    {
        double PosX = P.X + WindowSize.X / 2;
        double PosY = -P.Y + WindowSize.Y / 2;

        return new Vector(PosX, PosY);
    }

    public static Vector ConvertToCartesianCoordinates(Vector P)
    {
        double PosX = P.X - WindowSize.X / 2;
        double PosY = -P.Y + WindowSize.Y / 2;

        return new Vector(PosX, PosY);
    }
}
