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

  // Methods
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // initialize Timer
    timer = new Timer();
    countDownTimer();

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
          // loadNewQuestion
          timer.cancel();
          timer = new Timer();
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
          // loadNewQuestion
          timer.cancel();
          timer = new Timer();
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
          // loadNewQuestion
          timer.cancel();
          timer = new Timer();
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
          // loadNewQuestion
          timer.cancel();
          timer = new Timer();
          loadQuestionFromList();
        });
  }

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
