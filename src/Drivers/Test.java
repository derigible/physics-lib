package Drivers;

import Entities.Particle;
import Entities.Point;
import Entities.Structures.Bag;
import Entities.Structures.Iterates;
import Entities.Structures.ListQueue;
import Entities.Structures.ListStack;
import Newtonian.Kinematics.ParticleCollisionSystem;
import Newtonian.Kinematics.TwoD;

import java.util.Random;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Test{

    public static void main(String[] args){
        int scale = 50;
        Random rand = new Random();
        Point[] points = new Point[50];
        StdDraw.setScale(0, scale);
        StdDraw.setPenRadius(.01);
        for(int i = 0; i < 50; i++){
            points[i] = new Particle(rand.nextInt(scale), rand.nextInt(scale),
                    rand.nextDouble(), rand.nextDouble(), 0,
                    0, .01,1);
//            System.out.println(points.peek());
            StdDraw.point(points[i].x, points[i].y);
        }
        StdDraw.show(100);
        ParticleCollisionSystem pcs = new ParticleCollisionSystem(points, 50, 50);
        while(true){
            pcs.simulate();
        }
    }
}
