package members;

import com.example.jplquiz.controller.ClientQuestionView;
import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXMLLoader;

public class ClientThread implements Runnable {

  private final Thread thread;
  Socket socket = null;

  public ClientThread() {
    thread = new Thread(this);
  }

  @Override
  public void run() {

    try {
      socket = new Socket("localhost", 1234);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Client client = new Client(socket, "TestClient");
    System.out.println("Client listening for questions...");

    client.listenForQuestions();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("client-questionView.fxml"));
    ClientQuestionView clientQuestionView = loader.getController();

    System.out.println("Client transfering questions to controller...");
    client.transferQuestions(clientQuestionView);
  }

  public void execute() {
    thread.start();
  }
}
