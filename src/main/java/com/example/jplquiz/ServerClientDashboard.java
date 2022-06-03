package com.example.jplquiz;

import com.example.jplquiz.controller.ClientNickNameItem;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

public class ServerClientDashboard implements Initializable {

  @FXML private Button btnStart;

  @FXML private Label lb_playerCounter;

  @FXML private HBox playerItemsHbox;

  private int playerCounter;

  private ObservableList<String> observableList;

  private ArrayList<String> tempNameList;

  private Socket socket;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    tempNameList = new ArrayList<>();

    observableList = FXCollections.observableArrayList();

    createNodeFromItem();

    btnStart.setOnAction(
        actionEvent -> {
          sendStart();
        });

    observableList.addListener(
        ((InvalidationListener) observable -> Platform.runLater(this::createNodeFromItem)));
  }

  public void createNodeFromItem() {

    if (observableList != null) {
      try {
        for (String name : tempNameList) {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("client-nickNameItem.fxml"));
          Node node = fxmlLoader.load();

          ClientNickNameItem clientNickNameItem = fxmlLoader.getController();
          clientNickNameItem.setItemName(name);
          playerItemsHbox.getChildren().add(node);
          playerCounter++;
          lb_playerCounter.setText(String.valueOf(playerCounter));
        }
        tempNameList.clear();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @FXML
  void sendStart() {
    try {
      if(socket.isConnected()){
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(true);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addName(String name) {
    tempNameList.add(name);
    observableList.add(name);
    System.out.println(observableList);
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
