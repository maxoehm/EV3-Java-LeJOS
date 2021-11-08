package example;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;

public class Command {

    private String command;
    private boolean vorneBewegen;

    public Command(String identifier) {
    }


    public void run() {

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Command moveForward(EV3LargeRegulatedMotor mt1, EV3LargeRegulatedMotor mt2) {
        mt1.forward();
        mt2.forward();
        return null;
    }

    public void moveBackwards(EV3LargeRegulatedMotor mt1, EV3LargeRegulatedMotor mt2) {
        mt1.backward();
        mt2.backward();
    }

}
