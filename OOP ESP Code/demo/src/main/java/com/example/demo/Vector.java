package com.example.demo;

public class Vector {

    public double X;
    public double Y;
    
    public static Vector Zero = new Vector(0,0);

    public Vector(double x, double y)
    {
        X = x;
        Y = y;
    }

    public Vector MultiplyVectorWithScalar(double f)
    {
        return new Vector(X * f, Y * f);
    }


    public Vector DivideVectorWithScalar(double f)
    {
        return new Vector(X / f, Y / f);
    }

    public Vector AddVector(Vector v)
    {
        return new Vector(X + v.X, Y + v.Y);
    }

    public static double getDistance(Vector from, Vector to)
    {
        double Horizontal = to.X - from.X;
        double Vertical = to.Y - from.Y;
        return  (double)Math.sqrt(Horizontal*Horizontal + Vertical*Vertical);
    }

    public static Vector getDisplacement(Vector from, Vector to){
        return new Vector(to.X-from.X,to.Y- from.Y);
    }

    public static double getMagnitude(Vector v){
        return  (double)Math.sqrt(v.X * v.X + v.Y * v.Y);
    }

    public void printString(String s){
        System.out.println(s + " = " + X + ", " + Y);
    }
}
