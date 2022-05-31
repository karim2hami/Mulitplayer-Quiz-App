package com.example.jplquiz;

import com.example.jplquiz.ClientLoginView;
import com.example.jplquiz.controller.ClientQuestionView;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.Client;
import members.ClientThread;

public class ClientApp extends Application {

  Socket socket = null;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {

      FXMLLoader fxmlLoaderStart = new FXMLLoader(getClass().getResource("client-loginView.fxml"));
      Scene scene = new Scene(fxmlLoaderStart.load());
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();
      ClientLoginView clientLoginView = fxmlLoaderStart.getController();

      try {
        socket = new Socket("localhost", 1234);
      } catch (IOException e) {
        e.printStackTrace();
      }


      Client client = new Client(socket, "TestClient");
      clientLoginView.setClient(client);






    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
