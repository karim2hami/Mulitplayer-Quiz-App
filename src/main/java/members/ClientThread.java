package members;

import java.net.Socket;

public class ClientThread implements Runnable {

  private final Thread thread;
  Socket socket = null;

  public ClientThread() {
    thread = new Thread(this);
  }

  @Override
  public void run() {}

  public void execute() {
    thread.start();
  }
}
