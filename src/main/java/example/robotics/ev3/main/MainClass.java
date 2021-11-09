package example.robotics.ev3.main;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.robotics.tts.Espeak;
import ev3dev.sensors.Battery;
import ev3dev.sensors.ev3.EV3IRSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import example.CommunicationServer;
import example.robotics.ev3.sensor.USSensorExample;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MainClass {

    public static Logger LOGGER = LoggerFactory.getLogger(USSensorExample.class);

    private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

    private static EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    private static EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
    private final static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);

    private final static int motorSpeed = 200;

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

        if (ms.contains("sound")) {
            ms = ms.replace("sound", "");

            PlayMp3 playMp3 = new PlayMp3();
            playMp3.convert(ms);
            playMp3.play();

        }

        if (ms.contains("sag")) {
            ms = ms.replace("sag", "");
            communicationServer.sendMessage("Ich sage nun " + ms);

            leseVor(ms);

        }

        if (Objects.equals(ms, "bump")) {
            communicationServer.sendMessage("Doing as you wishing");
            bumperCar();
        }

    }

    private static void leseVor(String lesen) {
        Espeak TTS = new Espeak();

        TTS.setVoice("de");
        TTS.setSpeedReading(105);
        TTS.setPitch(60);
        TTS.setMessage(lesen);
        TTS.say();

    }

    private static void eineMethode() {
        LOGGER.info("Eine Methode wird gerade ausgeführt!");
    }

    private static void bumperCar() {
        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);

        final SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

        final int distance_threshold = 35;

        //Robot control loop
        final int iteration_threshold = 400;
        for(int i = 0; i <= iteration_threshold; i++) {
            forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];

            if(distance <= distance_threshold){
                System.out.println("Detected obstacle");
                backwardWithTurn();
            }

            communicationServer.sendMessage("Iteration: " + i);
            communicationServer.sendMessage("Battery: " + Battery.getInstance().getVoltage());
            communicationServer.sendMessage("Distance: " + distance);
            System.out.println();
        }

        motorRight.stop();
        motorLeft.stop();
    }

    private static void forward(){
        motorLeft.forward();
        motorRight.forward();
    }

    private static void backwardWithTurn(){
        motorLeft.backward();
        motorRight.backward();
        Delay.msDelay(1000);
        motorLeft.stop();
        motorRight.stop();
        motorLeft.backward();
        motorRight.forward();
        Delay.msDelay(1000);
        motorLeft.stop();
        motorRight.stop();
    }



}
