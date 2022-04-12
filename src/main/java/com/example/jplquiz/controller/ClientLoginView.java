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

  public Button getBtn_enter() {
    return btn_enter;
  }

  public void setBtn_enter(Button btn_enter) {
    this.btn_enter = btn_enter;
  }

  public String getTfd_nickname() {
    return tfd_nickname.getText();
  }

  public void setTfd_nickname(String tfd_nickname) {
    this.tfd_nickname.setText(tfd_nickname);
  }
}
