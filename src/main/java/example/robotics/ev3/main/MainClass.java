package example.robotics.ev3.main;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.EV3Key;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.SampleThread;

public class MainClass {

        final int motorSpeed = 200;

        public static void main(final String[] args) {

            System.out.println("Initialise Motors");
            final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
            final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

            final EV3Key returnButton = new EV3Key(EV3Key.BUTTON_BACKSPACE);

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    System.out.println("Emergency Stop");
                    motorLeft.stop();
                    motorRight.stop();
                }
            }));

            do {

                if (sensorCheck()) {

                }

            }while (returnButton.isUp());

        }






    public static boolean sensorCheck() {

            return false;
        }

    }
