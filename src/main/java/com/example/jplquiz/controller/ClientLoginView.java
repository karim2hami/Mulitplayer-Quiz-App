package com.example.jplquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import members.Client;

public class ClientLoginView {

  @FXML
  private Button btn_enter;

  @FXML
  private TextField tfd_nickname;

  @FXML
  void getNickName() {
    String username = tfd_nickname.getText();
    System.out.println(username);
  }

}
