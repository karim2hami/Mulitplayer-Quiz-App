package members;

import com.example.jplquiz.controller.ClientLoginView;
import java.io.IOException;
import java.net.Socket;


public class ClientThread implements Runnable {

	private Thread thread;

	public ClientThread() {
		thread = new Thread(this);

	}
	@Override
	public void run() {


		ClientLoginView clientLoginView = new ClientLoginView();
		String username = clientLoginView.getTfd_nickname();

		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Client client = new Client(socket, "username");
		client.listenForQuestions();
		client.sendMessage();

	}

	public void execute() {
		thread.start();
	}
}
