package members;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

  public static final List<ClientHandler> clientHandlers = new ArrayList<>();
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String clientUsername;

  /**
   * @param socket
   *     <Client Handler that is responsible for communication with Client and Server
   *     <OutputStream is wrapped with BufferWriter for sending characters and not bytes same is
   *     <for InputStream
   */
  public ClientHandler(Socket socket) {
    try {
      this.socket = socket;
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.clientUsername = bufferedReader.readLine();
      clientHandlers.add(this);
      broadcastMessage("Server: " + clientUsername + " has entered the game!");
    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  @Override // Listens for messages from the Client
  public void run() {
    String messageFromClient;

    while (socket.isConnected()) {
      try {
        messageFromClient = bufferedReader.readLine();
        System.out.println(clientUsername);
        broadcastMessage(messageFromClient);
      } catch (IOException e) {
        closeEverything(socket, bufferedReader, bufferedWriter);
        break;
      }
    }
  }

  // Send a message to all clients at the same time
  public void broadcastMessage(String messageToSend) {
    for (ClientHandler clientHandler : clientHandlers) {

      try {
        if (!clientHandler.clientUsername.equals(clientUsername)) {
          clientHandler.bufferedWriter.write(messageToSend);
          // clients wait for the new line
          clientHandler.bufferedWriter.newLine();
          clientHandler.bufferedWriter.flush();
        }
      } catch (IOException e) {
        closeEverything(socket, bufferedReader, bufferedWriter);
      }
    }
  }

  public void removeClientHandler() {
    clientHandlers.remove(this);
    broadcastMessage("Server : " + clientUsername + " has left the game!");
  }

  public void closeEverything(
      Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
    removeClientHandler();
    try {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (bufferedWriter != null) {
        bufferedWriter.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
