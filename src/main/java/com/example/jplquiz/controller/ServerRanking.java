package com.example.jplquiz.controller;

import com.example.jplquiz.models.ClientRankingModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import members.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;

public class ServerRanking implements Serializable {

    @FXML
    private Label labelRankOne;

    @FXML
    private Label labelRankTwo;
    @FXML
    private Label labelRankThree;


    private static final HashMap<String, Integer> namePointsMap = new HashMap<>();

    private final List<ClientRankingModel> clientRankingModelList = new ArrayList<>();
    private Socket socket;

    private Client client;

    int index = 0;


    /**
     * @author devinhasler
     * Thread that listens through a BufferedReader for the results, that the Clienthandlers send
     */
    public void listenForPoints() {
        BufferedReader bufferedReader = client.getBufferedReader();
        Thread thread = new Thread(
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
     * @param hm
     * @author devinhasler, karimtouhami
     * Sortes a Hasmap into a List that contains ClientRanking Models
     */
    public void sortByValue(Map<String, Integer> hm) {
        // Create a list from elements of HashMap
        clientRankingModelList.clear();
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        for (Map.Entry<String, Integer> aa : list) {
            clientRankingModelList.add(new ClientRankingModel(aa.getKey(), aa.getValue()));
        }

    }


    /**
     * @author devinhasler, karimtouhami
     * creates ClientRankingitem and loads them into the positions. Also it creates the podium and filles in the values
     */

    public void createNodeFromItem() {

        labelRankOne.setText(String.valueOf(clientRankingModelList.get(0).getPlayerNickName() + " " + clientRankingModelList.get(0).getPlayerScore()));
        if (clientRankingModelList.size() == 2) {
            labelRankTwo.setText(String.valueOf(clientRankingModelList.get(1).getPlayerNickName() + " " + clientRankingModelList.get(1).getPlayerScore()));
        } else if (clientRankingModelList.size() == 3) {
            labelRankTwo.setText(String.valueOf(clientRankingModelList.get(1).getPlayerNickName() + " " + clientRankingModelList.get(1).getPlayerScore()));
            labelRankThree.setText(String.valueOf(clientRankingModelList.get(2).getPlayerNickName() + " " + clientRankingModelList.get(2).getPlayerScore()));
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
