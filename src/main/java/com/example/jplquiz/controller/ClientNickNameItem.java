package com.example.jplquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author karimtouhami
 * ClientNichNameItem: Controller Class for Textitem, that is displayed on the server-clientDashboard.fxml
 * when a new Client enters the game.
 */

public class ClientNickNameItem {

  @FXML private Label lbNickname;

  public void setItemName(String nickName) {
    lbNickname.setText(nickName);
  }
}
