package com.example.jplquiz;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.Client;

/**
 * @author karimtouhami ClientApp: Starts a new JavaFX application that loads the
 *     "client-loginView.fxml" for the client part of the game, sets up a new Socket and starts a
 *     client instance
 */
public class ClientApp extends Application {

  Socket socket = null;

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * @author karimtouhami Loads client-loginView.fxml into the GUI and sets up a new client
   *     instance.
   *     <p>start(): The main entry point for all JavaFX applications. The start method is called
   *     after * the init method has returned, and after the system is ready for the application to
   *     begin running.
   * @param primaryStage - the primary stage for this application, onto which the application scene
   *     * is set.
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      setupSocket();
      FXMLLoader fxmlLoaderStart = new FXMLLoader(getClass().getResource("client-loginView.fxml"));

      Scene scene = new Scene(fxmlLoaderStart.load());

      ClientLoginView clientLoginView = fxmlLoaderStart.getController();
      clientLoginView.setSocket(socket);

      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author karimtouhami
   *     <p>Sets up a new Socket instance for communication between Server and Client on port
   *     "localhost:1234".
   */
  public void setupSocket() {
    try {
      socket = new Socket("localhost", 1234);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
