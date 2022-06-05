package com.example.jplquiz.controller;

import com.example.jplquiz.models.QuestionModel;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * @author devinhasler ClientQuestionView: Controller Class of client-questionView.fxml Displays the
 *     game and questions to the player Handles Validataion of the players answers Sends the players
 *     score back to the server.
 */
@SuppressWarnings("DuplicatedCode")
public class ClientQuestionView implements Initializable {

  @FXML private Button btnA;

  @FXML private Button btnB;

  @FXML private Button btnC;

  @FXML private Button btnD;

  @FXML private ImageView imgQuestion;

  @FXML private Label lbCountDown;

  @FXML private Label lbPlayerPoints;

  @FXML private Label lbQuestion;

  @FXML private Label lbQuestionCounter;

  private List<QuestionModel> questionModels;

  private List<Boolean> answers = new ArrayList<>();

  private Socket socket;

  private int playerScore = 0;

  private int falseAnswers = 0;

  private int correctAnswers = 0;

  private int questionsNumber = 1;

  private String rightAnswer;

  private Timer timer;

  /**
   * @author karimtouhami Method initialize is overrides the method from the Initializable Interface
   *     of the javafx.fxml package. The method is called to initialize the ClientQuestionView
   *     Controller after its root element has been completely processed.
   *     <p>Initialize a Timer object Add all event listeneners to the GUI buttons
   * @param url - The location used to resolve relative paths for the root object, or null if the
   *     location is not known.
   * @param resourceBundle - The resources are used to localize the root object, or null if the root
   *     object was not localized.
   */

  // Methods
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // initialize Timer
    timer = new Timer();
    countDownTimer();

    // initialize all buttons and their corresponding event listeners
    btnA.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button A was pressed...");
          // Validate value
          if (btnA.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
            lbPlayerPoints.setText(String.valueOf(playerScore));
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // Stop Timer and initialize a new one
          timer.cancel();
          timer = new Timer();
          // loadNewQuestion
          loadQuestionFromList();
        });

    btnB.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button B was pressed...");
          // Validate value
          if (btnB.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
            lbPlayerPoints.setText(String.valueOf(playerScore));
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // Stop Timer and initialize a new one
          timer.cancel();
          timer = new Timer();
          // loadNewQuestion
          loadQuestionFromList();
        });

    btnC.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button C was pressed...");
          // Validate value
          if (btnC.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
            lbPlayerPoints.setText(String.valueOf(playerScore));
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // Stop Timer and initialize a new one
          timer.cancel();
          timer = new Timer();
          // loadNewQuestion
          loadQuestionFromList();
        });

    btnD.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button D was pressed...");
          // Validate value
          if (btnD.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += Integer.parseInt(lbCountDown.getText()) * 100;
            lbPlayerPoints.setText(String.valueOf(playerScore));
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // Stop Timer and initialize a new one
          timer.cancel();
          timer = new Timer();
          // loadNewQuestion
          loadQuestionFromList();
        });
  }

  /**
   * @author devinhasler
   * Sets up an Outputstream of type object and writes the answers of the user to the stream.
   */

  @FXML
  public void sendAnswersToServer() {
    try {
      OutputStream outputStream = socket.getOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(answers);
      System.out.println("Sending of answers completed!");
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Sending of Answers failed");
    }
  }

  @FXML
  public void loadQuestionFromList() {

    if (questionsNumber < questionModels.size()) {
      lbQuestionCounter.setText(String.valueOf(questionsNumber));
      QuestionModel questionModel = questionModels.get(questionsNumber);
      lbQuestion.setText(questionModel.getQuestion());
      btnA.setText(questionModel.getAnswerA());
      btnB.setText(questionModel.getAnswerB());
      btnC.setText(questionModel.getAnswerC());
      btnD.setText(questionModel.getAnswerD());
      rightAnswer = questionModel.getRightAnswer();

      countDownTimer();
      questionsNumber++;

    } else {
      System.out.println("All questions answered, game finished...");
      // send answers back to server...
      System.out.println("Sending all answers to Server");
      sendAnswersToServer();
    }
  }

  @FXML
  public void countDownTimer() {
    System.out.println(timer);
    timer.scheduleAtFixedRate(
        new TimerTask() {
          int count = 31;

          @Override
          public void run() {

            if (count > 0) {
              Platform.runLater(() -> lbCountDown.setText(String.valueOf(count)));
              count--;
            } else {
              timer.cancel();
            }
          }
        },
        0,
        1000);
  }

  public void setQuestionModels(List<QuestionModel> questionModels) {
    this.questionModels = questionModels;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
