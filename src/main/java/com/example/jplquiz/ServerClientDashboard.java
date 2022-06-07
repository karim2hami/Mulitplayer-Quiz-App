package com.example.jplquiz;

import com.example.jplquiz.controller.ClientNickNameItem;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import members.Server;

/**
 * @author karimtouhami
 * <p>ServerClientDashboard: Controller class for "server-clientDashboard.fxml". Displays all
 * players, who have entered the game. Once all players have entered the game can be started
 * with the start Button in the GUI. All Clients then receive the questions and the
 * ClientQuestionView is started.
 */
public class ServerClientDashboard implements Initializable {

    @FXML
    private Button btnStart;
    @FXML
    private Label lbPlayerCounter;
    @FXML
    private HBox playerItemsBox;

    private int playerCounter;
    private ObservableList<String> observableList;
    private ArrayList<String> tempNameList;
    private Socket socket;
    private Server server;
    private boolean isStart;

    /**
     * @param url            - The location used to resolve relative paths for the root object, or null if the
     *                       location is not known.
     * @param resourceBundle - The resources are used to localize the root object, or null if the root
     *                       object was not localized.
     * @author karimtouhami
     * <p>Method initialize overrides the method from the Initializable Interface of the
     * javafx.fxml package. The method is called to initialize the ServerClientDashboard
     * Controller after its root element has been completely processed.
     * <p>Sets up a temporary list to store the player nicknames, which are sent from each client
     * on its ClientQuestionView. Then sets up an observableArrayList, which allows to track
     * changes when a new client entered the game. From every nickname in the temporay list, we
     * create new ClientNickNameItem instance and convert it into a Node object, and load it into
     * the GUI. Lastly we add the event listener to the start button to start the game.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isStart = false;
        tempNameList = new ArrayList<>();
        observableList = FXCollections.observableArrayList();

        createNodeFromItem();

        btnStart.setOnAction(actionEvent -> sendStart());
        observableList.addListener(
                ((InvalidationListener) observable -> Platform.runLater(this::createNodeFromItem)));
    }

    /**
     * @author devinhasler
     * <p>Iterates through the temporay list of nicknames and converts each of them into a Node
     * object and lastly adds them to the Hbox element and updates the GUI.
     */
    public void createNodeFromItem() {

        if (observableList != null) {
            try {
                for (String name : tempNameList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("client-nickNameItem.fxml"));
                    Node node = fxmlLoader.load();

                    ClientNickNameItem clientNickNameItem = fxmlLoader.getController();
                    clientNickNameItem.setItemName(name);
                    playerItemsBox.getChildren().add(node);
                    playerCounter++;
                    lbPlayerCounter.setText(String.valueOf(playerCounter));
                }
                tempNameList.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author devinhasler
     * <p>Sends the startmessage to all waiting clients to start the game.
     */
    @FXML
    void sendStart() {
        if (socket.isConnected()) {
            setStart(true);
            btnStart.setDisable(true);
            server.getClientHandler().broadcastMessage("true");
        }
    }

    /**
     * @param name - nickname from client.
     * @author devinhasler
     * <p>> Add a received nickname to the tempory list and observable array, only if the game has
     * not started yet.
     */
    public void addName(String name) {
        if (!isStart) {
            tempNameList.add(name);
            observableList.add(name);
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
