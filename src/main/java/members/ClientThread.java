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



  }

  public void execute() {
    thread.start();
  }
}
