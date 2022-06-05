package members;

import com.example.jplquiz.ServerClientDashboard;
import com.example.jplquiz.models.QuestionModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
  private Socket socket;
  private int numberOfClients = 0;
  private List<QuestionModel> questionModelList;
  private List<String> listOfClients;
  private ServerClientDashboard serverClientDashboard;
  private Thread listenForNamesThread;

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.listOfClients = new ArrayList<>();
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
        socket = serverSocket.accept();
        numberOfClients++;
        readQuestions("src/main/resources/Questions/Questions.csv");

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(questionModelList);

        serverClientDashboard.setSocket(socket);
        listenForNames();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author devinhasler
   *     <p>Thread listening for nicknames, which are being sent by the clients when entering the
   *     game and adding them to the ServerClientDashboard.
   */
  public void listenForNames() {
    listenForNamesThread =
        new Thread(
            () -> {
              while (!socket.isClosed()
                  && !serverClientDashboard.isStart()
                  && !listenForNamesThread.isInterrupted()) {
                try {
                  System.out.println(serverClientDashboard.isStart());
                  BufferedReader bufferedReader =
                      new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  Object object = bufferedReader.readLine();
                  listOfClients.add(String.valueOf(object));
                  serverClientDashboard.addName(String.valueOf(object));

                  System.out.println(object);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
    listenForNamesThread.start();
  }

  /**
   * @author devinhasler
   *     <p>Reads in the questions from a .csv file. The path to the .csv file is passed in as a
   *     String called filename
   * @param filename - Path to the .csv file containing the questions.
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
   * @author devinhasler
   *     <p>Creates a questionModel out of a line in the .csv file and seperates the question,
   *     possible answers und correct answer in order to create a new QuestionModel object, which is
   *     then returned.
   * @param data - Line extracted out of the .csv file
   * @return QuestionModel
   */
  public static QuestionModel createQuestionModel(String[] data) {
    if (data.length >= 5) {
      String question = data[0];
      String answerA = data[1];
      String answerB = data[2];
      String answerC = data[3];
      String answerD = data[4];
      String rightAnswer = data[5];

      return new QuestionModel(question, answerA, answerB, answerC, answerD, rightAnswer);
    }
    return null;
  }

  /**
   * @author devinhasler
   *     <p>Closes the ServerSocket connection.
   */
  public void closeServerSocket() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setServerClientDashboard(ServerClientDashboard serverClientDashboard) {
    this.serverClientDashboard = serverClientDashboard;
  }

  public Thread getListenForNamesThread() {
    return listenForNamesThread;
  }
}
