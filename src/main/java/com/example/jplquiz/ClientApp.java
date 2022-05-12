package com.example.jplquiz;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import members.Client;

public class ClientApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try{
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
          "client-loginView.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      ClientLoginView clientLoginView = fxmlLoader.getController();
//      ClientQuestionView clientQuestionView = fxmlLoader.getController();
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();

      Socket socket = null;
      try {
        socket = new Socket("localhost", 1234);
      } catch (IOException e) {
        e.printStackTrace();
      }


      Client client = new Client(socket, "devin");
      client.listenForQuestions();
      /*
      System.out.println("hallo");
      client.sendMessage();
      System.out.println("hallo2");
      Thread.sleep(100);
      client.transferQuestions(clientQuestionView);
//      ClientThread clientThread = new ClientThread();
//      clientThread.execute();



      ClientThread clientThread = new ClientThread();
      clientThread.execute();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);*/
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
