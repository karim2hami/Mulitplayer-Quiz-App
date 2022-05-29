package com.example.jplquiz;

import com.example.jplquiz.ClientLoginView;
import com.example.jplquiz.controller.ClientQuestionView;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
      ClientLoginView clientLoginView = fxmlLoaderStart.getController();
      primaryStage.setTitle("Multiplayer Quiz App");
      primaryStage.setScene(scene);
      primaryStage.show();

      if(clientLoginView.isReady()){
        FXMLLoader fxmlLoaderQuestion =
            new FXMLLoader(getClass().getResource("client-questionView.fxml"));
        ClientQuestionView clientQuestionView = fxmlLoaderQuestion.getController();
        Scene scene2 = new Scene(fxmlLoaderStart.load());
        primaryStage.setTitle("Multiplayer Quiz App");
        primaryStage.setScene(scene2);
        primaryStage.show();

        Client client = new Client(socket, "TestClient");
        System.out.println("Client listening for questions...");
        client.listenForQuestions();

        System.out.println("Client transfering questions to controller...");
        while (!client.isReady()) {
          System.out.println("Wait");
        }
        client.transferQuestions(clientQuestionView);
      }




      try {
        socket = new Socket("localhost", 1234);
      } catch (IOException e) {
        e.printStackTrace();
      }



    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
