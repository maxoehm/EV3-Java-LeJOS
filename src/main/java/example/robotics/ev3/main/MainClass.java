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

import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private static int motorSpeed = 200;

    public static Logger LOGGER = LoggerFactory.getLogger(USSensorExample.class);

    private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
    private static final EV3GyroSensor gyrosSensor = new EV3GyroSensor(SensorPort.S2);

    private static final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    private static final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);


    public static void main(final String[] args) {

        System.out.println("Initialise Motors");


        // Calibration of or Gyro Sensor
        gyrosSensor.reset();

        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);

        final EV3Key returnButton = new EV3Key(EV3Key.BUTTON_BACKSPACE);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motorLeft.stop();
                motorRight.stop();
            }
        }));

        do {

            if (!sensorCheck()) {
                motorLeft.forward();
                motorRight.forward();
            } else {



            }

        }while (returnButton.isUp());

    }

    public static boolean sensorCheck() {

        final SampleProvider sp = us1.getDistanceMode();
        int distanceValue = 0;

        final int iteration_threshold = 100;
        for (int i = 0; i <= iteration_threshold; i++) {

            float[] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int) sample[0];

            LOGGER.info("Iteration: {}, Distance: {}", i, distanceValue);
            if (distanceValue >= 10) {
                return  true;
            }

            Delay.msDelay(500);
        }

        return false;
    }

    private static void wallie() {

        motorLeft.rotate(90);
        motorRight.rotate(90);


    }

    private static int HALF_SECOND = 500;
    private static int LOOP_LIMIT = 100;


    private static void gyrosMoveValues() {

        gyrosSensor.reset();

        final SampleProvider sp = gyrosSensor.getAngleMode();
        int value = 0;

        int iterationCounter = 0;
        boolean gyrosSuccess = true;

        while (gyrosSuccess) {

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            value = (int)sample[0];

            LOGGER.info("Gyro angle: {}", value);

            if(value >= 90){
                Sound.getInstance().beep();
                LOGGER.info("Rotated 90 degrees");
                break;
            }

            //Avoid an infinite loop
            iterationCounter++;
            if (iterationCounter >= LOOP_LIMIT) {
                LOGGER.debug("Breaking the loop if you didn't rotate in 100 iterations");
                break;
            }

            Delay.msDelay(HALF_SECOND);
        }



    }


    private static List<String> personality() {

        List<String> otis = new ArrayList<String>();

        return otis;
    }

}
