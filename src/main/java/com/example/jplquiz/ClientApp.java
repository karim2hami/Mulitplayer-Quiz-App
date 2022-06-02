package com.example.jplquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.ClientThread;

public class ClientApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client-loginView.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();

      ClientThread clientThread = new ClientThread();
      clientThread.execute();

    } catch (Exception e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}
