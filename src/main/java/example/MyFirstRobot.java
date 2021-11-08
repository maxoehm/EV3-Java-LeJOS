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

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static EV3LargeRegulatedMotor motorLeft;
    public static EV3LargeRegulatedMotor motorRight;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
//
        System.out.println("Initialisiere Server Client");
         motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
         motorRight = new EV3LargeRegulatedMotor(MotorPort.B);

        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Command Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject("Packaged received: "+message);
            //close resources
            ois.close();
            oos.close();
            interpretCommand(message);
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit"))  {
                socket.close();
                break;
            }

        }
        //To Stop the motor in case of pkill java for example
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
            }
        }));

        System.exit(0);
    }

    private static void interpretCommand(String message) {
        System.out.println(message);

        if (message == "vorne") {
            motorLeft.forward();
            motorRight.forward();
        }



    }

}
