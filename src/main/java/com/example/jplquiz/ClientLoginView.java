package com.example.jplquiz;

import com.example.jplquiz.controller.ClientQuestionView;
import com.example.jplquiz.models.QuestionModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import members.Client;

/**
 * @author karimtouhami ClientLoginView: Controller class for the login GUI "client-loginView.fxml".
 *     The client can enter his nickname and enter a game. The nickname is then sent back to the
 *     server.
 */
public class ClientLoginView implements Initializable {

  @FXML private Button btnEnter;
  @FXML private TextField tfdNickname;
  @FXML private Label title;
  @FXML private VBox whiteBackground;

  private BufferedReader bufferedReader;
  private Socket socket;
  private Client client;
  private FXMLLoader questionListLoader;
  private boolean isStart = false;
  private int index = 0;
  private boolean nickNameSent = false;

  private ObservableList<String> observableList;

  /**
   * @author karimtouhami Method initialize overrides the method from the Initializable Interface of
   *     the javafx.fxml package. The method is called to initialize the ClientLoginView Controller
   *     after its root element has been completely processed.
   *     <p>Initialize a Timer object Add all event listeneners to the GUI buttons
   * @param url - The location used to resolve relative paths for the root object, or null if the
   *     location is not known.
   * @param resourceBundle - The resources are used to localize the root object, or null if the root
   *     object was not localized.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    observableList = FXCollections.observableArrayList();

    observableList.addListener(
        ((InvalidationListener) observable -> Platform.runLater(this::changeToClientQuestionView)));

    questionListLoader = new FXMLLoader(getClass().getResource("client-questionView.fxml"));

    btnEnter.setOnAction(
        actionEvent -> {
          listenForStart();
          if (!nickNameSent) {
            String userName = tfdNickname.getText();
            client.setUserName(userName);
            client.sendMessage();
            sendNickName(userName);

          } else if (isStart) {
            changeToClientQuestionView();
          }
        });
  }

  /**
   * Initializes a BufferedWriter in order to send the clients nickname back to the server.
   *
   * @param nickName - Not null, nickname the client entered.
   */
  @FXML
  void sendNickName(String nickName) {
    try {
      BufferedWriter bufferedWriter = client.getBufferedWriter();
      bufferedWriter.write(nickName);
      bufferedWriter.newLine();
      bufferedWriter.flush();
      title.setText("Waiting for the Game to start...");
      nickNameSent = true;

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Initializes a new Stage and loads the client-questionView.fxml in the GUI. */
  @FXML
  void changeToClientQuestionView() {
    try {
      client.listenForQuestions();
      Stage stage = new Stage();
      Scene scene = new Scene(questionListLoader.load());
      ClientQuestionView clientQuestionView = questionListLoader.getController();
      clientQuestionView.setClient(client);
      client.setClientQuestionView(clientQuestionView);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.setTitle("Multiplayer Quiz App");
      stage.show();
      client.transferQuestions();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage clientLoginView = (Stage) btnEnter.getScene().getWindow();
    clientLoginView.close();
  }

  /**
   * @author devinhasler Method listenForStart creates a new Thread, whose task is to wait until the
   *     questions are sent by the server to the client and the game is ready to start. Once ready
   *     the scene is changed to the "client-questionView.fxml" and the game begins.
   */
  public void listenForStart() {
    bufferedReader = client.getBufferedReader();
    new Thread(
            () -> {
              while (socket.isConnected()) {
                try {
                  String message = bufferedReader.readLine();
                  System.out.println(message);
                  if (message.equals("true")) {
                    Platform.runLater(this::changeToClientQuestionView);
                  }
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public void setStart(boolean start) {
    isStart = start;
  }
}
