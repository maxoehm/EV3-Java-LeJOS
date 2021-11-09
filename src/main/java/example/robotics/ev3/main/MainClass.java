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

        String ms = message.toLowerCase();
        System.out.println(message);

        if (Objects.equals(ms, "vorne")) {
            do {
                motorRight.forward();
                motorLeft.forward();
                communicationServer.sendMessage("Running...");
            } while (motorLeft.getTachoCount() < 1);
            motorLeft.stop();
            motorRight.stop();
        }

        if (Objects.equals(ms, "hinten")) {
            motorLeft.backward();
            motorRight.backward();
            communicationServer.sendMessage("Running...");
        }

        if (Objects.equals(ms, "links")) {
            do {
                motorRight.forward();
            } while (motorLeft.getTachoCount() < 1);
            motorLeft.stop();
        }

        if (Objects.equals(ms, "rechts")) {
            do {
                motorRight.forward();
            }  while (motorLeft.getTachoCount() < 1);
                motorLeft.stop();
        }

        if (Objects.equals(ms, "ping")) {
        communicationServer.sendMessage("Pong!");
        }

        if (Objects.equals(ms, "stop")) {
            communicationServer.sendMessage("Stopped!");

        }

        if (Objects.equals(ms, "eine Methode")) {
            communicationServer.sendMessage("Doing as you wishing");
            eineMethode();
        }

    }

    private static void eineMethode() {
        LOGGER.info("Eine Methode wird gerade ausgeführt!");
    }

}
