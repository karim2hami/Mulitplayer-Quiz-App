package members;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread implements Runnable {

	private final Thread thread;
	ServerSocket serverSocket = null;

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
		server.startServer();
	}

	public void execute() {
		thread.start();
	}
}
