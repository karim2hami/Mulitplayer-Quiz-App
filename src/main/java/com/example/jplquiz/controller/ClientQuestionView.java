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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ClientQuestionView implements Initializable {

  @FXML private Button btn_A;

  @FXML private Button btn_B;

  @FXML private Button btn_C;

  @FXML private Button btn_D;

  @FXML private ImageView img_question;

  @FXML private Label lb_countDown;

  @FXML private Label lb_playerPoints;

  @FXML private Label lb_question;

  @FXML private Label lb_questionCounter;

  private List<QuestionModel> questionModels;

  private List<Boolean> answers = new ArrayList<>();

  private int playerScore = 0;

  private int falseAnswers = 0;

  private int correctAnswers = 0;

  private int questionsNumber = 0;

  private String rightAnswer;

  private boolean game = true;

  // Methods
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // initialize Timer
    countDownTimer();

    btn_A.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button A was pressed...");
          game = false;
          // Validate value
          if (btn_A.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += 100;
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // loadNewQuestion
          loadQuestionFromList();
        });

    btn_B.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button B was pressed...");
          game = false;
          // Validate value
          if (btn_B.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += 100;
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // loadNewQuestion
          loadQuestionFromList();
        });

    btn_C.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button C was pressed...");
          game = false;
          // Validate value
          if (btn_C.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += 100;
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // loadNewQuestion
          loadQuestionFromList();
        });

    btn_D.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button D was pressed...");
          game = false;
          // Validate value
          if (btn_D.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
            answers.add(true);
            correctAnswers++;
            playerScore += 100;
          } else {
            System.out.println("WRONG ANSWER...");
            answers.add(false);
            falseAnswers++;
          }
          // loadNewQuestion
          loadQuestionFromList();
        });
  }

  @FXML
  public void sendAnswersToServer() {
    try {
      Socket socket = new Socket("localhost", 1234);
      System.out.println("Opened new Socket on localhost and port 1234");
      OutputStream outputStream = socket.getOutputStream();
      System.out.println("Initialized new Outputstream");
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
  public void updateScore() {
    lb_playerPoints.setText(String.valueOf(playerScore));
    lb_questionCounter.setText(String.valueOf(questionsNumber + 1));
  }

  @FXML
  public void loadQuestionFromList() {

    if (questionsNumber < questionModels.size()) {
      QuestionModel questionModel = questionModels.get(questionsNumber);
      System.out.println("Current questionModel: " + questionModel);

      lb_question.setText(questionModel.getQuestion());
      btn_A.setText(questionModel.getAnswerA());
      btn_B.setText(questionModel.getAnswerB());
      btn_C.setText(questionModel.getAnswerC());
      btn_D.setText(questionModel.getAnswerD());
      rightAnswer = questionModel.getRightAnswer();

      questionsNumber++;
      countDownTimer();
    } else {
      System.out.println("All questions answered, game finished...");
      // send answers back to server...
      System.out.println("Sending all answers to Server");
      sendAnswersToServer();
    }
  }

  @FXML
  public void countDownTimer() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          int count = 31;

          @Override
          public void run() {
            if (game) {
              if (count > 0) {
                Platform.runLater(() -> lb_countDown.setText(String.valueOf(count)));
                count--;
              } else {
                timer.cancel();
              }
            } else {
              timer.cancel();
            }
          }
        },
        0,
        1000);
  }

  // Getter and Setter
  public String getBtn_A() {
    return btn_A.getText();
  }

  public void setBtn_A(String btn_A) {
    this.btn_A.setText(btn_A);
  }

  public String getBtn_B() {
    return btn_B.getText();
  }

  public void setBtn_B(String btn_B) {
    this.btn_B.setText(btn_B);
  }

  public String getBtn_C() {
    return btn_C.getText();
  }

  public void setBtn_C(String btn_C) {
    this.btn_C.setText(btn_C);
  }

  public String getBtn_D() {
    return btn_D.getText();
  }

  public void setBtn_D(String btn_D) {
    this.btn_D.setText(btn_D);
  }

  public Image getImg_question() {
    return img_question.getImage();
  }

  public void setImg_question(String img_path) {
    this.img_question.setImage(
        new Image(String.valueOf(getClass().getResource("../icons/" + img_path + ".png"))));
  }

  public String getLb_countDown() {
    return lb_countDown.getText();
  }

  public void setLb_countDown(String lb_countDown) {
    this.lb_countDown.setText(lb_countDown);
  }

  public String getLb_playerPoints() {
    return lb_playerPoints.getText();
  }

  public void setLb_playerPoints(String lb_playerPoints) {
    this.lb_playerPoints.setText(lb_playerPoints);
  }

  public Label getLb_question() {
    return lb_question;
  }

  public void setLb_question(String lb_question) {
    this.lb_question.setText(lb_question);
  }

  public String getLb_questionCounter() {
    return lb_questionCounter.getText();
  }

  public void setLb_questionCounter(String lb_questionCounter) {
    this.lb_questionCounter.setText(lb_questionCounter);
  }

  public List<QuestionModel> getQuestionModels() {
    return questionModels;
  }

  public void setQuestionModels(List<QuestionModel> questionModels) {
    this.questionModels = questionModels;
  }
}
