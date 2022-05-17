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
  public void listenForMessage() {
    new Thread(
            new Runnable() {
              @Override
              public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                  try {
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                  } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                  }
                }
              }
            })
        .start();
  }

  public void listenForQuestions(){
    new Thread(
            () -> {
              while (socket.isConnected()) {
                try {
                  InputStream inputStream = socket.getInputStream();
                  ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                  try {

                    this.questionModelList = (List<QuestionModel>) objectInputStream.readObject();
                    System.out.println("question model list" + questionModelList);


                  } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                  }
                } catch (IOException e) {
                  closeEverything(socket, bufferedReader, bufferedWriter);
                }
              }
            })
        .start();


  }

  public void transferQuestions(ClientQuestionView clientQuestionView) {
    System.out.println("array list client: " + questionModelList);
    clientQuestionView.setQuestionModels(questionModelList);
    clientQuestionView.loadQuestionFromList();
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

  public List<QuestionModel> getQuestionModelList() {
    return questionModelList;
  }

  public void setQuestionModelList(List<QuestionModel> questionModelList) {
    this.questionModelList = questionModelList;
  }
}
