package Entities;

/**
 * Created by marcphillips on 5/21/2014.
 */
public class Particle extends Point{

    public Particle(){
        super();
        radius = 0;
        mass = 0;
    }

    public Particle(double x, double y, double radius, double mass){
        super(x,y,0,0,0,0);
        this.radius = radius;
        this.mass = mass;
    }

    public Particle(double x, double y, double velocity_x, double velocity_y,
                    double accel_x, double accel_y, double radius, double mass){
        super(x,y,velocity_x,velocity_y,accel_x,accel_y);
        this.radius = radius;
        this.mass = mass;
    }

    public String toString(){
        return super.toString() +
                "Rad: " + radius + "\n" +
                "Mass: " + mass + "\n";
    }
}
