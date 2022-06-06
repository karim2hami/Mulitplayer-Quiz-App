package com.example.jplquiz.controller;

import com.example.jplquiz.models.ClientRankingModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class ServerRanking {

    @FXML
    private Label title;
    @FXML
    private Label labelRankOne;

    @FXML
    private Label labelRankTwo;
    @FXML
    private Label labelRankThree;

    @FXML
    private VBox rankingBox;


    private static HashMap<String, Integer> namePointsMap = new HashMap<>();

    private List<ClientRankingModel> clientRankingModelList;

    private Socket socket;




    public void sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        for (Map.Entry<String, Integer> aa : list) {
            clientRankingModelList.add(new ClientRankingModel(aa.getKey(), aa.getValue()));
        }

    }


    public void listenForRankings() {
        new Thread(
                () -> {
                    while (namePointsMap.isEmpty()) {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            namePointsMap = (HashMap<String, Integer>) objectInputStream.readObject();
                            sortByValue(namePointsMap);
                            createNodeFromItem();
                            System.out.println(namePointsMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .start();
    }

    public void createNodeFromItem() {

        labelRankOne.setText(String.valueOf(clientRankingModelList.get(0)));
        if(clientRankingModelList.size() >= 2){
          labelRankTwo.setText(String.valueOf(clientRankingModelList.get(1)));
          labelRankThree.setText(String.valueOf(clientRankingModelList.get(2)));
        }


        int index = 1;
        if (namePointsMap != null) {
            try {
                for (ClientRankingModel clientRankingModel : clientRankingModelList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("client-rankingItem.fxml"));
                    Node node = fxmlLoader.load();

                    ClientRankingItem clientRankingItem = fxmlLoader.getController();
                    clientRankingItem.setPlayerRanking(new Label(String.valueOf(index)));
                    clientRankingItem.setGameScore(new Label(String.valueOf(clientRankingModel.getPlayerScore())));
                    rankingBox.getChildren().add(node);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
