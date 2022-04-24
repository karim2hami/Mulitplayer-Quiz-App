package com.example.jplquiz.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ServerClientDashboard {

  private Node node;

  @FXML private Button btn_start;

  @FXML private Label lb_playerCounter;

  @FXML private HBox playerItemsHbox;

  public ServerClientDashboard() throws IOException {
  }

  public void setLb_playerCounter(int number) {
    lb_playerCounter.setText(Integer.toString(number));
  }

  public void addPlayerToView(String playerNickName) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("client-nickNameItem.fxml"));
    node = fxmlLoader.load();
    ClientNickNameItem clientNickNameItem = fxmlLoader.getController();
    clientNickNameItem.setItemName(playerNickName);
    playerItemsHbox.getChildren().add(node);
  }

  public void closeEverything(
      Socket socket, BufferedReader bufferedReader) {
    try {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
