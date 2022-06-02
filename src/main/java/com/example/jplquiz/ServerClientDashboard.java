package com.example.jplquiz;

import com.example.jplquiz.controller.ClientNickNameItem;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ServerClientDashboard implements Initializable {

  @FXML private Button btn_start;

  @FXML private Label lb_playerCounter;

  @FXML private HBox playerItemsHbox;

  private int playerCounter;

  private ObservableList<String> observableList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    observableList = FXCollections.observableArrayList();

    createNodeFromItem();

    observableList.addListener(
        ((InvalidationListener)
            observable -> {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  createNodeFromItem();
                }
              });

            }));
  }

  public void createNodeFromItem() {
    if (observableList != null) {
      try {
        for (String name : observableList) {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("client-nickNameItem.fxml"));
          Node node = fxmlLoader.load();

          ClientNickNameItem clientNickNameItem = fxmlLoader.getController();
          clientNickNameItem.setItemName(name);
          playerItemsHbox.getChildren().add(node);
          playerCounter++;
          lb_playerCounter.setText(String.valueOf(playerCounter));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public void addName(String name){
    observableList.add(name);
  }
}
