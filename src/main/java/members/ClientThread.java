package members;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Thread thread;

	public ClientThread() {
		thread = new Thread(this);
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your username for the game: ");
		String username = scan.nextLine();
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Client client = new Client(socket, username);
		client.listenForMessage();
		client.listenForQuestions();
		client.sendMessage();
	}

	public void execute() {
		thread.start();
	}
}
