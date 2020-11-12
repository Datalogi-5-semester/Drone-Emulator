package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.*;

public class Controller {

    public TableView tableViewLog;
    public TableColumn logColumnCommand;
    public TableColumn logColumnTime;

    public TableView tableViewSavedPackages;
    public TextField asciiField;
    public TextField addressField;
    public TextField portField;
    public TableColumn savedColumnSend;
    public TableColumn savedColumnName;
    public TableColumn savedColumnToAddress;
    public TableColumn savedColumnToPort;
    public TableColumn savedColumnAscii;
    public TableColumn savedColumnHex;
    public Button saveUdpMessage;
    public Button lalala;
    public Circle droneCircle;
    public Arc droneArc;
    public ColorPicker colorPicker;
    public Button clearLogButton;
    public TextField speedField;
    public int speedLabel;


    Drone drone;

    private ObservableList<UdpPackage> savedPackages = FXCollections.observableArrayList();
    private ObservableList<UdpPackage> loggedPackages = FXCollections.observableArrayList();

    private UdpPackageReceiver receiver;
    private DatagramSocket sender;

    public void initialize() throws UnknownHostException {

        this.drone = new Drone(100, 100, droneArc);

        droneArc.setFill(colorPicker.getValue());

        System.out.println("creates list of packages");
        UdpPackage test1 = new UdpPackage("name", "data", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000, 4000);
        UdpPackage test2 = new UdpPackage("name", "hello world", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000, 4000);
        loggedPackages.addAll(test1, test2);
        savedPackages.addAll(test1, test2);

        //add list of items to table
        tableViewLog.setItems(loggedPackages);

        //set columns content
        logColumnTime.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("formattedDate")
        );

        logColumnCommand.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsString")
        );


       /* tableViewSavedPackages.setItems(savedPackages);

        savedColumnSend.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("")
        );
        savedColumnName.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("name")
        );
        savedColumnToAddress.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("toIp")
        );
        savedColumnToPort.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("toPort")
        );
        savedColumnAscii.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("dataAsString")
        );
        savedColumnHex.setCellValueFactory(
                new PropertyValueFactory<sample.UdpPackage, String>("dataAsHex")
        );
        */


        //add udp server/receiver
        receiver = new UdpPackageReceiver(loggedPackages, drone, 6000);
        new Thread(receiver).start();

        //create udp sender
        try {
            sender = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void sendUdpMessage(ActionEvent actionEvent) {

        // sends a basic test message to localhost port 4000!

        String message = asciiField.getText();

        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(addressField.getText()), Integer.parseInt(portField.getText()));
            sender.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUdpMessage(ActionEvent actionEvent) {

        try {
            UdpPackage saved1 = new UdpPackage("name", asciiField.getText(), InetAddress.getByName(addressField.getText()), InetAddress.getByName("127.0.0.1"), 4000, Integer.parseInt(portField.getText()));
            savedPackages.addAll(saved1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearLog() {
        loggedPackages.clear();
    }

}
