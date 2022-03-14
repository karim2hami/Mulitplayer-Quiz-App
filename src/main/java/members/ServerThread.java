package members;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread implements Runnable {

	private Thread thread;

	public ServerThread() {
		thread = new Thread(this);
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Server server = new Server(serverSocket);
		server.startServer();
	}

	public void execute() {
		thread.start();
	}
}
