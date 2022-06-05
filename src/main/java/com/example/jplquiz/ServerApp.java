package com.example.jplquiz;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.ServerThread;

/**
 * @author karimtouhami ServerApp: Starts a new JavaFX application that loads the
 *     "server-clientDashboard.fxml" for the server part of the game, sets up a new Serverthread
 *     that starts a new server instance.
 */
public class ServerApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Loads server-clientDashboard.fxml into the GUI and sets up a new ServerThread instance.
   *
   * <p>start(): The main entry point for all JavaFX applications. The start method is called after
   * the init method has returned, and after the system is ready for the application to begin
   * running.
   *
   * @param primaryStage - the primary stage for this application, onto which the application scene
   *     is set.
   */
  @Override
  public void start(Stage primaryStage) {
    try {

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("server-clientDashboard.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();

      ServerThread serverThread = new ServerThread();
      serverThread.setServerClientDashboard(fxmlLoader.getController());
      serverThread.execute();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
