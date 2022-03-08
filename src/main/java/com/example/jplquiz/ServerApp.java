package com.example.jplquiz;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try{
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
          "server-clientDashboard.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
