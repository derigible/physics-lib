package Newtonian.Kinematics;

/**
 * Created by marcphillips on 5/19/2014.
 */
import Entities.Point;

public final class TwoD{

    private TwoD(){
        //Prevent instantiation
    }

    public static double distance_x(Point p1, Point p2){
        return p1.x - p2.x;
    }

    public static double distance_y(Point p1, Point p2){
        return p1.y - p2.y;
    }

    public static double distance_plane(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static double move_x(Point p, double t, double radius, double x_wall){
        if(p.x + p.getVelocity_x()*t + ((p.getAccel_x()*Math.pow(t,2))/2) < radius ||
                p.x + p.getVelocity_x()*t + ((p.getAccel_x()*Math.pow(t,2))/2) > x_wall - radius){
            p.reflect_x();
        }
        return p.x + p.getVelocity_x()*t + ((p.getAccel_x()*Math.pow(t,2))/2);
    }

    public static double move_y(Point p, double t, double radius, double y_wall){
        if(p.y + p.getVelocity_y()*t + ((p.getAccel_y()*Math.pow(t,2))/2) < radius ||
                p.y + p.getVelocity_y()*t + ((p.getAccel_y()*Math.pow(t,2))/2) > y_wall - radius){
            p.reflect_y();
        }
        return p.y + p.getVelocity_y()*t + ((p.getAccel_y()*Math.pow(t,2))/2);
    }

    /**
     * probably shouldn't use this... let me look into it more for now
     * @param p
     * @param t
     * @return
     */
    public static double move_plane(Point p, double t){
        return 0 + TwoD.calc_velocity_plane(p,t)*t + ((TwoD.calc_accel_plane(p) * Math.pow(t,2))/2);
    }

    public static double calc_velocity_x(Point p, double t){
        return p.getAccel_x()*t + p.getVelocity_x();
    }

    public static double calc_velocity_y(Point p, double t){
        return p.getAccel_y()*t + p.getVelocity_y();
    }

    public static double calc_velocity_plane(Point p, double t){
        return Math.sqrt(Math.pow(
                TwoD.calc_velocity_x(p, t), 2) + Math.pow(
                TwoD.calc_velocity_y(p, t), 2));
    }

    public static double calc_accel_plane(Point p){
        return Math.sqrt(Math.pow(p.getAccel_x(), 2) + Math.pow(p.getAccel_y(), 2));
    }

    public static double calc_new_accel_x(Point p, double newVelocity_x, double t){
        double i = TwoD.calc_velocity_x(p,0);
        double vi = p.getVelocity_x(); //This is an idempotent method, save initial state
        p.setVelocity_x(newVelocity_x);
        double f = (i - TwoD.calc_velocity_x(p,t))/t;
        p.setVelocity_x(vi);
        return f;
    }

    public static double calc__new_accel_y(Point p, double newVelocity_y, double t){
        double i = TwoD.calc_velocity_y(p,0);
        double vi = p.getVelocity_y(); //This is an idempotent method, save initial state
        p.setVelocity_y(newVelocity_y);
        double f = (i - TwoD.calc_velocity_y(p,t))/t;
        p.setVelocity_y(vi);
        return f;
    }

    public static double calc_new_accel_plane(Point p, double newVelocity_x, double newVelocity_y, double t){
        double nvp = Math.sqrt(Math.pow(
                p.getAccel_x()*t + newVelocity_x, 2) + Math.pow(
                p.getAccel_y()*t + newVelocity_y, 2));
        return (nvp - TwoD.calc_velocity_plane(p,t))/t;
    }

    public static double get_velocity_x_y_cross_product(Point p1, Point p2){
        double change_v_x = p1.getVelocity_x() - p2.getVelocity_x();
        double change_v_y = p1.getVelocity_x() - p2.getVelocity_x();
        return change_v_x*change_v_x + change_v_y*change_v_y;
    }

    public static double get_velocity_distance_cross_product(Point p1, Point p2){
        double change_x = p1.x - p2.x;
        double change_y = p2.y - p2.y;
        double change_v_x = p1.getVelocity_x() - p2.getVelocity_x();
        double change_v_y = p1.getVelocity_x() - p2.getVelocity_x();
        return change_v_x*change_x + change_v_y*change_y;
    }

    public static double get_distance_x_y_cross_product(Point p1, Point p2){
        double change_x = p1.x - p2.x;
        double change_y = p2.y - p2.y;
        return change_x*change_x + change_y*change_y;
    }
}
