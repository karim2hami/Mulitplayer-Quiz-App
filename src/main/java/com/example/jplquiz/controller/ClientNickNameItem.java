package com.example.jplquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientNickNameItem {

  @FXML private Label lb_nickname;

  public void setItemName(String nickName) {
    lb_nickname.setText(nickName);
  }
}
