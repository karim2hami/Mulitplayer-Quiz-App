package com.example.jplquiz;

import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.Client;

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

      setupSocket();

      Client client = new Client(socket, "TestClient");
      clientLoginView.setClient(client);
      clientLoginView.setSocket(socket);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setupSocket() {
    try {
      socket = new Socket("localhost", 1234);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
