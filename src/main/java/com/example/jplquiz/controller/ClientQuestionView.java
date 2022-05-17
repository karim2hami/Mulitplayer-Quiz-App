package com.example.jplquiz.controller;

import com.example.jplquiz.models.QuestionModel;
import java.net.URL;
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

  private String rightAnswer;

  // Methods
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // initialize Timer
    countDownTimer();

    btn_A.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button A was pressed...");
          // Validate value
          if (btn_A.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
          } else {
            System.out.println("WRONG ANSWER...");
          }
          // return value to server
        });
    btn_B.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button B was pressed...");
          // Validate value
          if (btn_B.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
          } else {
            System.out.println("WRONG ANSWER...");
          }
          // return value to server
        });
    btn_C.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button C was pressed...");
          // Validate value
          if (btn_C.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
          } else {
            System.out.println("WRONG ANSWER...");
          }
          // return value to server
        });
    btn_D.setOnMouseClicked(
        actionEvent -> {
          System.out.println("Button D was pressed...");
          // Validate value
          if (btn_D.getText().equals(rightAnswer)) {
            System.out.println("Right Answer!");
          } else {
            System.out.println("WRONG ANSWER...");
          }
          // return value to server
        });
  }

  @FXML
  public void loadQuestionFromList(int questionNumber) {

    QuestionModel questionModel = questionModels.get(questionNumber);
    System.out.println("hallo" + questionModel);

    lb_question.setText(questionModel.getQuestion());
    btn_A.setText(questionModel.getAnswerA());
    btn_B.setText(questionModel.getAnswerB());
    btn_C.setText(questionModel.getAnswerC());
    btn_D.setText(questionModel.getAnswerD());
    rightAnswer = questionModel.getRightAnswer();
  }

  @FXML
  public void countDownTimer() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          int count = 31;

          @Override
          public void run() {
            if (count > 0) {
              Platform.runLater(() -> lb_countDown.setText(String.valueOf(count)));
              count--;
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
