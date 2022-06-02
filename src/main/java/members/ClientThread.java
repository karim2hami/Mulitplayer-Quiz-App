package members;

import com.example.jplquiz.controller.ClientQuestionView;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

  private final Thread thread;
  Socket socket = null;

  public ClientThread() {
    thread = new Thread(this);
  }

  @Override
  public void run() {
    ClientQuestionView clientQuestionView = new ClientQuestionView();

    try {
      socket = new Socket("localhost", 1234);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Client client = new Client(socket, "TestClient");
    System.out.println("Client listening for questions...");
    client.listenForQuestions();

    System.out.println("Client sending message...");
    client.sendMessage();

    System.out.println("Client transfering questions to controller...");
    client.transferQuestions(clientQuestionView);
  }

  public void execute() {
    thread.start();
  }
}
