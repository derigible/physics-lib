package Newtonian.Kinematics;

import Drivers.StdDraw;
import Entities.Point;
import Entities.Structures.ListMinPriorityQueue;

/**
 * Created by marcphillips on 5/21/2014.
 */
public class ParticleCollisionSystem{
    private ListMinPriorityQueue<Event> events;
    private double t = 0.0;
    private double x_wall, y_wall;
    private Point[] points;

    public ParticleCollisionSystem(Point[] points, double x_wall, double y_wall){
        this.points = points;
        this.x_wall = x_wall;
        this.y_wall = y_wall;
    }

    private void predict(Point a){
        if(a == null) return;
        for(int i = 0; i < points.length; i++){
            double timeChange = a.timeToHit(points[i]);
            System.out.println(timeChange);
            events.push(new Event(t + timeChange, a, points[i]));
        }
        events.push(new Event(t + a.timeToHitHorizontalWall(x_wall) , a, null));
        events.push(new Event(t + a.timeToHitVerticalWall(y_wall) , null, a));
    }

    public void simulate(){
        events = new ListMinPriorityQueue<Event>();
        for(int i = 0; i < points.length; i++){
            predict(points[i]);
        }
        events.push(new Event(0, null, null));

        while(!events.isEmpty()){
            Event e = events.pull();
            if(!e.isValid()) continue;
            Point p1 = e.p1;
            Point p2 = e.p2;

            for(int i = 0; i < points.length; i++){
                points[i].move(e.time - t, x_wall, y_wall);
            }
            t = e.time;
            if(t < 0) System.out.println("less");
            if(p1 != null && p2 != null) p1.bounceOff(p2);
            else if (p1 != null && p2 == null) p1.bounceOffVertical();
            else if (p1 == null && p2 != null) p2.bounceOffHorizontal();
            else if (p1 == null && p2 == null) redraw();

            predict(p1);
            predict(p2);
        }
    }

    private void redraw(){

        System.out.println("Redrawing");
        StdDraw.show(100);
    }

    private class Event implements Comparable<Event>{
        private double time;
        private Point p1, p2;
        private int countA, countB;
        public Event(double t, Point a, Point b){
            time = t;
            this.p1 = a;
            this.p2 = b;
            if(a != null){
                countA = a.collisions;
            } else {
                countA = -1;
            }
            if(b != null){
                countB = b.collisions;
            } else {
                countB = -1;
            }
        }

        @Override
        public int compareTo(Event other){
            return this.time > other.time ? 1 : this.time == other.time ? 0 : -1;
        }

        public boolean isValid(){
            if(countA == -1 && countB == -1){
                return true;
            } else if(countA == -1 && countB != -1){
                return countB == p2.collisions;
            } else {
                return countA == p1.collisions;
            }
        }
    }
}
