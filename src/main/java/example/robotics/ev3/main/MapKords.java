package example.robotics.ev3.main;

public class MapKords {


    private double xKord;
    private double yKord;
    private double zKord;

    private boolean isBlocked;
    private String information;
    int i = 0;

    public MapKords(Double x, Double y, Boolean blocked) {
        this.xKord = x;
        this.yKord = y;
        this.isBlocked = blocked;
    }


    public MapKords(Double x, Double y, Boolean blocked, String information) {
        this.xKord = x;
        this.yKord = y;
        this.isBlocked = blocked;
        this.information = information;

    }

    public double getxKord() {
        return xKord;
    }

    public void setxKord(double xKord) {
        this.xKord = xKord;
    }

    public double getyKord() {
        return yKord;
    }

    public void setyKord(double yKord) {
        this.yKord = yKord;
    }

    public double getzKord() {
        return zKord;
    }

    public void setzKord(double zKord) {
        this.zKord = zKord;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
