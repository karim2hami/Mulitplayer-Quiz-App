package com.example.jplquiz.controller;

import com.example.jplquiz.members.Client;
import com.example.jplquiz.models.ClientRankingModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServerRanking {

  @FXML public Label title;
  @FXML private Label labelRankOne;
  @FXML private Label labelRankTwo;
  @FXML private Label labelRankThree;

  private static final HashMap<String, Integer> namePointsMap = new HashMap<>();
  private final List<ClientRankingModel> clientRankingModelList = new ArrayList<>();
  private Socket socket;

  private Client client;

  /**
   * @author devinhasler Thread that listens through a BufferedReader for the results, that the
   *     Clienthandlers send
   */
  public void listenForPoints() {
    BufferedReader bufferedReader = client.getBufferedReader();
    Thread thread =
        new Thread(
            () -> {
              while (socket.isConnected() && !Thread.currentThread().isInterrupted()) {
                try {
                  String message = bufferedReader.readLine();
                  String[] namesPointsArray = message.split(";");

                  namePointsMap.put(namesPointsArray[0], Integer.parseInt(namesPointsArray[1]));

                  sortByValue(namePointsMap);
                  Platform.runLater(this::createNodeFromItem);

                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
    thread.start();
  }

  /**
   * @param hm - HashMap of player name and player score
   * @author devinhasler, karimtouhami Sortes a Hasmap into a List that contains ClientRanking
   *     Models
   */
  public void sortByValue(Map<String, Integer> hm) {
    // Create a list from elements of HashMap
    clientRankingModelList.clear();
    List<Map.Entry<String, Integer>> list = new LinkedList<>(hm.entrySet());

    // Sort the list
    list.sort(Map.Entry.comparingByValue());
    Collections.reverse(list);

    for (Map.Entry<String, Integer> aa : list) {
      clientRankingModelList.add(new ClientRankingModel(aa.getKey(), aa.getValue()));
    }
  }

  /**
   * @author devinhasler, karimtouhami creates ClientRankingitem and loads them into the positions.
   *     Also, it creates the podium and filles in the values
   */
  public void createNodeFromItem() {

    labelRankOne.setText(
        clientRankingModelList.get(0).playerNickName()
            + " "
            + clientRankingModelList.get(0).playerScore());
    if (clientRankingModelList.size() == 2) {
      labelRankTwo.setText(
          clientRankingModelList.get(1).playerNickName()
              + " "
              + clientRankingModelList.get(1).playerScore());
    } else if (clientRankingModelList.size() == 3) {
      labelRankTwo.setText(
          clientRankingModelList.get(1).playerNickName()
              + " "
              + clientRankingModelList.get(1).playerScore());
      labelRankThree.setText(
          clientRankingModelList.get(2).playerNickName()
              + " "
              + clientRankingModelList.get(2).playerScore());
    }
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
