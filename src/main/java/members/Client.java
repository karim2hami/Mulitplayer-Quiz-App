package members;

import com.example.jplquiz.controller.ClientQuestionView;
import com.example.jplquiz.models.QuestionModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class Client {

  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String userName;
  private List<QuestionModel> questionModelList;

  private ClientQuestionView clientQuestionView;

  public Client(Socket socket, String userName) {
    try {
      this.socket = socket;
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.userName = userName;

    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  public void sendMessage() {
    try {
      if (socket.isConnected()) {
        String messageToSend = "hallo";
        bufferedWriter.write(userName + ": " + messageToSend);
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }
    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  /** listenForMessage listens to messages that are broadcasted from the ClientHandler */

  // Listen for Question models from Server
  public void listenForQuestions() {
    new Thread(
            () -> {
              while (questionModelList == null) {
                try {
                  InputStream inputStream = socket.getInputStream();
                  ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                  readObjectForQuestion(objectInputStream);
                } catch (IOException e) {
                  closeEverything(socket, bufferedReader, bufferedWriter);
                }
              }
            })
        .start();
  }

  public void readObjectForQuestion(ObjectInputStream objectInputStream){
    try {
      this.questionModelList = (List<QuestionModel>) objectInputStream.readObject();
      System.out.println(questionModelList);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public void transferQuestions() {
    System.out.println("array list client " + questionModelList);


    clientQuestionView.setSocket(socket);
    clientQuestionView.setQuestionModels(questionModelList);
    if(questionModelList != null){
      clientQuestionView.loadQuestionFromList();
    }

  }

  public void closeEverything(
      Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

  public void setClientQuestionView(ClientQuestionView clientQuestionView) {
    this.clientQuestionView = clientQuestionView;
  }

  public void setQuestionModelList(
      List<QuestionModel> questionModelList) {
    this.questionModelList = questionModelList;
  }
}
