package members;

import com.example.jplquiz.ServerClientDashboard;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author karimtouhami
 *     <p>ServerThread: Initializes a new Server and ServerSocket instance and executes them in a
 *     seperate Thread.
 */
public class ServerThread implements Runnable {

  private final Thread thread;
  private ServerSocket serverSocket = null;
  private ServerClientDashboard serverClientDashboard;

  public ServerThread() {
    thread = new Thread(this);
  }

  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(1234);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Server server = new Server(serverSocket);
    serverClientDashboard.setServer(server);
    server.setServerClientDashboard(serverClientDashboard);
    server.startServer();
  }

  public void execute() {
    thread.start();
  }

  public void setServerClientDashboard(ServerClientDashboard serverClientDashboard) {
    this.serverClientDashboard = serverClientDashboard;
  }
}
