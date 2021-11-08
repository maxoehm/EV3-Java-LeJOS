package example;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyFirstRobot {

    private static int index = 0;



    public static EV3LargeRegulatedMotor motorLeft;
    public static EV3LargeRegulatedMotor motorRight;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
//
        System.out.println("Initialisiere Server Client");
         motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
         motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

         CommunicationServer communicationServer = new CommunicationServer();
         communicationServer.start(66666);


    }

    private static void interpretCommand(String message) {
        System.out.println(message);

        if (message == "vorne") {
            motorLeft.forward();
            motorRight.forward();
        }



    }

}
