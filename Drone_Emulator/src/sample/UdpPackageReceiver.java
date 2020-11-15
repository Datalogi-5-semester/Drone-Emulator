package sample;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class UdpPackageReceiver implements Runnable {

    boolean running = false;
    DatagramSocket socket;
    private byte[] buf = new byte[256];
    int port;

    List udpPackages;
    Drone drone;
    TextField speedField;
    TextField xField;
    TextField yField;
    TextField heightField;
    TextField statusField;


    public UdpPackageReceiver(List udpPackages, Drone drone, TextField speedField, TextField xCoord, TextField yCoord,
                              TextField heightField, TextField statusField, int port) {
        this.drone = drone;
        this.speedField = speedField;
        this.xField = xCoord;
        this.yField = yCoord;
        this.heightField = heightField;
        this.statusField = statusField;
        this.running = true;
        this.udpPackages = udpPackages;
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            updateHeight();
            updateCoords();
            updateSpeed();
            updateStatus();
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                System.out.println("Command received");
                UdpPackage udpPackage = new UdpPackage(packet.getData());
                udpPackages.add(0, udpPackage);
                parseIncomingMessage(udpPackage);
                buf = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseIncomingMessage(UdpPackage udpPackage) {
        String command = udpPackage.getDataAsString().trim();

        switch (command) {
            case "takeoff" -> drone.takeOff();
            case "up" -> drone.heightUp();
            case "down" -> drone.heightDown();
            case "land" -> drone.land();
            case "faster" -> drone.speedUp();
            case "slower" -> drone.speedDown();
            case "forward" -> drone.goForward();
            case "backwards" -> drone.goBackwards();
            case "right" -> drone.goRight();
            case "left" -> drone.goLeft();
            case "turnright" -> drone.turnRight();
            case "turnleft" -> drone.turnLeft();
        }
    }

    public void updateSpeed() {
        speedField.setText(String.valueOf(drone.getSpeed()));
    }

    public void updateCoords() {
        xField.setText(String.valueOf(drone.getX()));
        yField.setText(String.valueOf(drone.getY()));
    }

    public void updateHeight() {
        heightField.setText(String.valueOf(drone.getHeight()));
    }

    public void updateStatus() {
        if (drone.isOnGround()) {
            statusField.setText("On the ground!");
        } else {
            statusField.setText("In the air!");
        }
    }
}
