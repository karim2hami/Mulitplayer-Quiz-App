package com.example.jplquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientNickNameItem {

  @FXML private Label lbNickname;

  public void setItemName(String nickName) {
    lbNickname.setText(nickName);
  }
}
