package example.robotics.ev3.main;

import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class MainClass {

    public static void main(final String[] args) {


    }

    public static boolean sensorCheck() {

        final EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S4);
        final SampleProvider sampleProv = new getDistanceMode();
        int distanz;


        float [] sample = new float[sampleProv.sampleSize()];


        }

    }

    private static class getDistanceMode implements SampleProvider {
        @Override
        public int sampleSize() {
            return 0;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {

        }


    }
}
