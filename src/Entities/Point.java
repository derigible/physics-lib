package Entities;

import Newtonian.Kinematics.TwoD;

/**
        * Created by marcphillips on 5/19/2014.
        * A basic model of a point on a 2D plain.
        */
public class Point{
    public double x;
    public double y;
    /**
     * Should not be changed for base class.
     */
    public double radius = 0;
    /**
     * Should not be changed for base class.
     */
    public double mass = 0;
    public int collisions;
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

    public void move(double t, double x_wall, double y_wall){
        x = TwoD.move_x(this, t, radius, x_wall);
        y = TwoD.move_y(this, t, radius, y_wall);
    }

    public double timeToHit(Point that){
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx = that.x - this.x, dy = that.y - this.y;
        double dvx = that.velocity_x - this.velocity_x, dvy = that.velocity_y - this.velocity_y;
        double dvdr = dx*dvx + dy*dvy;
        if( dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double timeToHitVerticalWall(double wall){
        return (wall - radius - x)/velocity_x;
    }

    public double timeToHitHorizontalWall(double wall){
        return (wall - radius - y)/velocity_y;
    }

    public void bounceOff(Point other){
        double dx = other.x - this.x, dy = other.y - this.y;
        double dvx = other.getVelocity_x() - this.velocity_x;
        double dvy = other.getVelocity_y() - this.velocity_y;
        double dvdr = dx*dvx + dy*dvy;
        double dist = this.radius + other.radius;
        double J = 2 * this.mass * other.mass * dvdr / ((this.mass + other.mass) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;
        this.velocity_x += Jx / this.mass;
        this.velocity_y += Jy / this.mass;
        other.setVelocity_x(other.getVelocity_x() - Jx / other.mass);
        other.setVelocity_y(other.getVelocity_y() - Jy / other.mass);
        this.collisions++;
        other.collisions++;
    }

    public void bounceOffVertical(){
        velocity_x = -velocity_x;
        collisions++;
    }

    public void bounceOffHorizontal(){
        velocity_y = -velocity_y;
        collisions++;
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
