package com.example.jplquiz.members;

import com.example.jplquiz.ServerClientDashboard;
import com.example.jplquiz.models.QuestionModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karimtouhami
 *     <p>Server: Class starting the server instance, reading in the questions from the .csv file up
 *     the connection between server and client, and transfers the received questions from the
 *     server to the ClientQuestionView GUI.
 */
public class Server {

  private final ServerSocket serverSocket;

  private ClientHandler clientHandler;
  private List<QuestionModel> questionModelList;
  private ServerClientDashboard serverClientDashboard;

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  /**
   * @author devinhasler
   *     <p>Starts the Serversocket that accepts all connecting client sockets. Then reads all
   *     questions out of the "Questions.csv" file. Then writes the qustions to an
   *     objectoutputstream.
   */
  public void startServer() {
    try {
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        readQuestions("src/main/resources/Questions/Questions.csv");

        sendObject(questionModelList, socket);

        clientHandler = new ClientHandler(socket);
        clientHandler.setServerClientDashboard(serverClientDashboard);
        Thread thread = new Thread(clientHandler);
        thread.start();

        serverClientDashboard.setSocket(socket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param object object that should be sent to Client with ObjectOutputStream
   * @param socket socket that was given for the communication
   * @throws IOException
   *     <p>Sends ClientQuestionModelList to all the Clients
   * @author devinhasler
   */
  public void sendObject(Object object, Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(object);
  }

  /**
   * @param filename - Path to the .csv file containing the questions.
   * @author devinhasler
   *     <p>Reads in the questions from a .csv file. The path to the .csv file is passed in as a
   *     String called filename
   */
  public void readQuestions(String filename) {
    List<QuestionModel> questions = new ArrayList<>();
    Path pathToFile = Paths.get(filename);

    try (BufferedReader bufferedReader =
        Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
      String line = bufferedReader.readLine();

      while (line != null) {
        String[] attributes = line.split(";");
        QuestionModel questionModel = createQuestionModel(attributes);
        questions.add(questionModel);
        line = bufferedReader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    questionModelList = questions;
  }

  /**
   * @param data - Line extracted out of the .csv file
   * @return QuestionModel
   * @author devinhasler
   *     <p>Creates a questionModel out of a line in the .csv file and seperates the question,
   *     possible answers und correct answer in order to create a new QuestionModel object, which is
   *     then returned.
   */
  public static QuestionModel createQuestionModel(String[] data) {
    if (data.length >= 6) {
      String question = data[0];
      String answerA = data[1];
      String answerB = data[2];
      String answerC = data[3];
      String answerD = data[4];
      String rightAnswer = data[5];
      String imagePath = data[6];

      return new QuestionModel(
          question, answerA, answerB, answerC, answerD, rightAnswer, imagePath);
    }
    return null;
  }

  public void setServerClientDashboard(ServerClientDashboard serverClientDashboard) {
    this.serverClientDashboard = serverClientDashboard;
  }

  public ClientHandler getClientHandler() {
    return clientHandler;
  }
}
