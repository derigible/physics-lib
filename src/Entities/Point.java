package Entities;

/**
        * Created by marcphillips on 5/19/2014.
        * A basic model of a point on a 2D plain.
        */
public class Point{
    public double x;
    public double y;
    private double velocity_x;
    private double velocity_y;
    private double accel_x;
    private double accel_y;

    /**
     * Creates an empty Point object that is centered on zero
     * and has no velocity or acceleration.
     */
    public Point(){
        init(0,0,0,0,0,0);
    }

    public Point(double x, double y){
        init(x,y,0,0,0,0);
    }

    public Point(double x, double y, double velocity_x, double velocity_y, double accel_x, double accel_y){
        init(x,y,velocity_x,velocity_y,accel_x,accel_y);
    }

    private void init(double x, double y, double velocity_x, double velocity_y, double accel_x, double accel_y){
        this.x = x;
        this.y = y;
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
        this.accel_x = accel_x;
        this.accel_y = accel_y;
    }

    public void reflect_x(){
        Point.reflect_x(this);
    }

    public void reflect_y(){
        Point.reflect_y(this);
    }

    public static void reflect_x(Point p){
        p.x = p.x * -1;
        p.setVelocity_x(p.getVelocity_x() * -1);
        p.setAccel_x(p.getAccel_x());
    }

    public static void reflect_y(Point p){
        p.y = p.y * -1;
        p.setVelocity_y(p.getVelocity_y() * -1);
        p.setAccel_y(p.getAccel_y() + -1);
    }

    public double getVelocity_x(){
        return velocity_x;
    }

    public void setVelocity_x(double velocity){
        this.velocity_x = velocity;
    }

    public double getVelocity_y(){
        return velocity_y;
    }

    public void setVelocity_y(double velocity){
        this.velocity_y = velocity;
    }

    public double getAccel_x(){
        return accel_x;
    }

    public void setAccel_x(double accel_x){
        this.accel_x = accel_x;
    }

    public double getAccel_y(){
        return accel_y;
    }

    public void setAccel_y(double accel_y){
        this.accel_y = accel_y;
    }

    @Override
    public String toString(){
        return "X: "+ x + "\n" +
                "Y: "+ y + "\n" +
                "VX: "+ velocity_x + "\n" +
                "VY: "+ velocity_y + "\n" +
                "AX: "+ accel_x + "\n" +
                "AY: "+ accel_y + "\n";
    }
}
