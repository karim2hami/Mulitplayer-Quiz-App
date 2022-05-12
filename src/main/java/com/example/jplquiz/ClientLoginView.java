package com.example.jplquiz;

import com.example.jplquiz.controller.ClientQuestionView;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientLoginView implements Initializable {

  Socket socket;

  @FXML private Button btn_enter;

  @FXML private TextField tfd_nickname;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    btn_enter.setOnAction(
        actionEvent -> {
          String nickName = tfd_nickname.getText();
          System.out.println("New player: " + nickName);
          try {
            System.out.println("Trying to connect to server...");
            socket = new Socket("localhost", 1234);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            System.out.println("Trying to send nickname to server...");
            objectOutputStream.writeObject(nickName);
            System.out.println("Success!");
            System.out.println("Changing to Clientquestionview...");
            changeToClientQuestionView();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  @FXML
  void changeToClientQuestionView() {
    try{
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("client-questionView.fxml"));
      Scene scene = new Scene(loader.load());

      stage.setScene(scene);
      stage.setResizable(false);
      stage.setTitle("Multiplayer Quiz App");
      stage.show();
      System.out.println("Opened Clientquestionview");
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage clientLoginView = (Stage) btn_enter.getScene().getWindow();
    clientLoginView.close();
    System.out.println("Closed clientLoginView!");
  }
}
