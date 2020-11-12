package sample;

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

    public UdpPackageReceiver(List udpPackages, Drone drone, int port) {
        this.drone = drone;
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
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                System.out.println("package arrived!");
                UdpPackage udpPackage = new UdpPackage("name", packet.getData(), packet.getAddress(), socket.getLocalAddress(), packet.getPort(), socket.getLocalPort());
                udpPackages.add(0, udpPackage);
                //method(udpPackage);
                parseIncomingMessage(udpPackage);
                buf = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseIncomingMessage(UdpPackage hej) {
        String besked = hej.getDataAsString().trim();

       /* if (besked.equals("op")){
            drone.goUp();
        } */

        switch (besked) {
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

        System.out.println(besked);


    }
}
