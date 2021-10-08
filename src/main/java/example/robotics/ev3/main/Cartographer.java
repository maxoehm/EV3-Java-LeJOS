package example.robotics.ev3.main;

public class Cartographer {


    private float oldDistance = 100;

    public Cartographer() {

    }


    public void newPoint(float newD) {
        getDistance(newD);

        this.oldDistance = newD;

    }

    private float getDistance(float newD) {
        return oldDistance - newD;
    }




}
