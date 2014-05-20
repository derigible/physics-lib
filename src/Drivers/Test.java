package Drivers;

import Entities.Point;
import Entities.Structures.Bag;
import Entities.Structures.Iterates;
import Entities.Structures.ListQueue;
import Entities.Structures.ListStack;
import Newtonian.Kinematics.TwoD;

import java.util.Random;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Test{

    public static void main(String[] args){
        int scale = 50;
        Random rand = new Random();
        Iterates<Point> points = new ListQueue<Point>();
        StdDraw.setScale(0, scale);
        StdDraw.setPenRadius(.01);
        for(int i = 0; i < 5000; i++){
            points.push(new Point(rand.nextDouble() * scale, rand.nextDouble() * scale,
                    rand.nextDouble(), rand.nextDouble(), 0,
                    0));
//            System.out.println(points.peek());
            StdDraw.point(points.peek().x, points.peek().y);
        }
        StdDraw.show(100);
        while(true){
            StdDraw.clear();
            StdDraw.setScale(0, scale);
            StdDraw.setPenRadius(.01);
            for(Point p : points){
                if(TwoD.move_x(p,1) > 50){
                    p.x = 50;
                    p.setVelocity_x(p.getVelocity_x() * -1);
                } else if (TwoD.move_x(p,1) < 0){
                    p.x = 0;
                    p.setVelocity_x(p.getVelocity_x() * -1);
                } else{
                        p.x = TwoD.move_x(p, 1);
                }
                if(TwoD.move_y(p, 1) > 50){
                    p.y = 50;
                    p.setVelocity_y(p.getVelocity_y() * -1);
                } else if(TwoD.move_y(p, 1) < 0){
                    p.y = 0;
                    p.setVelocity_y(p.getVelocity_y() * -1);
                } else{
                    p.y = TwoD.move_y(p, 1);
                }
                StdDraw.point(p.x, p.y);
            }
            StdDraw.show(100);
        }
    }
}
