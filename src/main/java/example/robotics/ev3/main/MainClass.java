package example.robotics.ev3.main;

import ev3dev.actuators.Sound;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.EV3Key;
import ev3dev.sensors.ev3.EV3GyroSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import example.robotics.ev3.sensor.USSensorExample;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainClass {

    private static int HALF_SECOND = 500;
    private static int LOOP_LIMIT = 100;
    private static int motorSpeed = 200;

    public static Logger LOGGER = LoggerFactory.getLogger(USSensorExample.class);

    private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
    private static final EV3GyroSensor gyrosSensor = new EV3GyroSensor(SensorPort.S2);

    private static final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    private static final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

    private static HashMap map = new HashMap();
    private static int index = 0;


    public static void main(final String[] args) {

        System.out.println("Initialise Motors");


        // Calibration of or Gyro Sensor
        gyrosSensor.reset();

        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);


        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motorLeft.stop();
                motorRight.stop();
            }
        }));




        final EV3Key leftButton = new EV3Key(EV3Key.BUTTON_LEFT);

        System.out.println("press LEFT to start the programme :)");

        while (true) {
                if (leftButton.isDown()) {

                    wallie();
                }
}


    }

    private static Double x = 0.0;
    private static Double y = 0.0;

    public static void wallie() {

        final EV3Key returnButton = new EV3Key(EV3Key.BUTTON_BACKSPACE);

        do {

            if (!sensorCheck()) {
                defaultSettings();
                motorLeft.forward();
                motorRight.forward();
            } else {
                gyrosMoveValues();
            }

            updateMap(x, y++);
        }while (returnButton.isUp());

    }

    private static void updateMap(Double x, Double y) {

        map.put(index, new Point2D.Double(x, y));
        index++;
        System.out.println(map.get(0));

    }

    private static void defaultSettings() {

        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);

    }

    public static boolean sensorCheck() {

        final SampleProvider sp = us1.getDistanceMode();
        int distanceValue = 0;

        final int iteration_threshold = 100;

            float[] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int) sample[0];
            LOGGER.info("Iteration: {}", distanceValue);

            Cartographer map = new Cartographer();
            map.newPoint(distanceValue);

            if (distanceValue <= 170 && distanceValue >= 160) {
                Sound.getInstance().playTone(440, HALF_SECOND);
            }
            else if (distanceValue <= 120 && distanceValue >= 110) {

                Sound.getInstance().playTone(523, HALF_SECOND);
            }
            else if (distanceValue <= 60) {
                        Sound.getInstance().playTone(659, HALF_SECOND);
                        return true;
            }

        return false;
    }

    private static void rotateRobot(EV3LargeRegulatedMotor rotateLR) {

        rotateLR.setSpeed(0);
        LOGGER.info("rotateRobot ");

        motorLeft.forward();
        motorRight.forward();

    }




    private static void gyrosMoveValues() {

        gyrosSensor.reset();

        final SampleProvider sp = gyrosSensor.getAngleMode();
        int value = 0;

        int iterationCounter = 0;
        boolean gyrosSuccess = true;

        float [] sample = new float[sp.sampleSize()];


        while (gyrosSuccess) {

            sp.fetchSample(sample, 0);
            value = (int)sample[0];

            LOGGER.info("Gyro angle: {}", value);
            if(value < 80){
                rotateRobot(motorRight);
                Sound.getInstance().beep();

            } else {
                Sound.getInstance().playTone(440, 100);
                Sound.getInstance().playTone(523, 100);
                Sound.getInstance().playTone(659, 100);
                gyrosSuccess = false;
            }

            //Avoid an infinite loop
            iterationCounter++;
            if (iterationCounter >= LOOP_LIMIT) {
                LOGGER.debug("Breaking the loop if you didn't rotate in 100 iterations");
                gyrosSuccess = false;
            }

            Delay.msDelay(HALF_SECOND);
        }


    }

}
