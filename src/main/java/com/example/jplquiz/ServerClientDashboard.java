package com.example.jplquiz;

import com.example.jplquiz.controller.ClientNickNameItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import members.Server;

public class ServerClientDashboard implements Initializable {

  @FXML
  private Button btn_start;

  @FXML
  private Label lb_playerCounter;

  @FXML
  private HBox playerItemsHbox;

  private int playerCounter;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    createNodeFromItem();
  }

  public void createNodeFromItem() {
    try{
      for(int i = 0; i < 10; i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("client-nickNameItem.fxml"));
        Node node = fxmlLoader.load();

        ClientNickNameItem clientNickNameItem = fxmlLoader.getController();
        clientNickNameItem.setItemName("PlayerName" + i);
        playerItemsHbox.getChildren().add(node);
        playerCounter++;
        lb_playerCounter.setText(String.valueOf(playerCounter));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
