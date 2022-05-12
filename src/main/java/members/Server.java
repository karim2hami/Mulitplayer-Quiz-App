package members;

import com.example.jplquiz.models.QuestionModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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

public class Server{

  private final ServerSocket serverSocket;
  private ClientHandler clientHandler;
  public int numberOfClients = 0;
  private List<QuestionModel> questionModelList;

  public Server(ServerSocket serverSocket) {

    this.serverSocket = serverSocket;
  }

  public int transferNumberOfClients() {
    return this.numberOfClients;
  }

  public void startServer() {
    try {
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        System.out.println("A new Client has connected");
        numberOfClients++;

        clientHandler = new ClientHandler(socket);

        Thread thread = new Thread(clientHandler);
        thread.start();

        readQuestions("src/main/resources/Questions/Questions.csv");

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(questionModelList);
        listenForMessages();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listenForMessages() throws IOException {
    Socket socket = new Socket("localhost", 1234);
    new Thread(
        () -> {
          while (socket.isConnected()) {
            try {
              InputStream inputStream = socket.getInputStream();
              ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
              String message = (String) objectInputStream.readObject();
              System.out.println("Message from client: " + message);
            } catch (IOException e) {
              e.printStackTrace();
            } catch (ClassNotFoundException e) {
              throw new RuntimeException(e);
            }
          }
        })
        .start();
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
    if(data.length >= 5){
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
}
