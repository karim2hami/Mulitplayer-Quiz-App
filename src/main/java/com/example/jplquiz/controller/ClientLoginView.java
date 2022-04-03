package com.example.jplquiz.controller;

import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import members.Client;

public class ClientLoginView {

  private Client client = new Client(new Socket("localhost",1234));

  @FXML private Button btn_enter;

  @FXML private TextField tfd_nickname;

  public ClientLoginView() throws IOException {
  }

  @FXML
  void getNickName() throws IOException {
    String username = tfd_nickname.getText();
//    System.out.println(username);
    client.getMessageFromController(username);
  }
}
