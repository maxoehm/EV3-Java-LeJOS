package example.robotics.ev3.main;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import example.CommunicationServer;
import example.robotics.ev3.sensor.USSensorExample;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class MainClass {

    public static Logger LOGGER = LoggerFactory.getLogger(USSensorExample.class);

    private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

    private static EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    private static EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

    private static CommunicationServer communicationServer;

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Initialisiere Server Client");
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
        motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

        communicationServer = new CommunicationServer();
        communicationServer.start(33334);

        while (true) {
            interpretCommand(communicationServer.receiveMessage());
        }

    }

    private static void interpretCommand(String message) {
        System.out.println(message);

        if (Objects.equals(message, "vorne")) {
            motorLeft.forward();
            motorRight.forward();
        }

        if (Objects.equals(message, "ping")) {
        communicationServer.sendMessage("Pong!");
        }


    }

}
