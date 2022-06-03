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

public class Server {

  private final ServerSocket serverSocket;

  private Socket socket;
  public int numberOfClients = 0;
  private List<QuestionModel> questionModelList;

  private List<String> listOfClients;

  private ServerClientDashboard serverClientDashboard;


  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.listOfClients = new ArrayList<>();
  }


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
        listenforNames();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listenforNames() throws IOException {
    new Thread(
            () -> {
              while (!socket.isClosed()) {
                try {
                  BufferedReader bufferedReader =
                      new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  String name = bufferedReader.readLine();
                  listOfClients.add(name);
                  serverClientDashboard.addName(name);
                  System.out.println(name);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }

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
}
