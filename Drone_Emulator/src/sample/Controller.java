package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Arc;

import java.net.*;

public class Controller {

    public TableView tableViewLog;
    public TableColumn logColumnCommand;
    public TableColumn logColumnTime;

    public Arc droneArc;
    public Button clearLogButton;
    public TextField speedField;
    public TextField xField;
    public TextField yField;
    public TextField heightField;
    public TextField statusField;
    Drone drone;

    private ObservableList<UdpPackage> loggedPackages = FXCollections.observableArrayList();

    private UdpPackageReceiver receiver;

    public void initialize() throws UnknownHostException {


        this.drone = new Drone(droneArc);

        //add list of items to table
        tableViewLog.setItems(loggedPackages);

        //set columns content
        logColumnTime.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("formattedDate")
        );

        logColumnCommand.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsString")
        );

        //add udp server/receiver
        receiver = new UdpPackageReceiver(loggedPackages, drone, speedField, xField, yField, heightField, statusField, 6000);
        new Thread(receiver).start();

    }

    public void clearLog() {
        loggedPackages.clear();
    }

}
