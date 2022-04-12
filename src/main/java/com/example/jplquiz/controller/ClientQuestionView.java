package com.example.jplquiz.controller;

import com.example.jplquiz.models.QuestionModel;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClientQuestionView {

  @FXML
  private Button btn_A;

  @FXML
  private Button btn_B;

  @FXML
  private Button btn_C;

  @FXML
  private Button btn_D;

  @FXML
  private ImageView img_question;

  @FXML
  private Label lb_countDown;

  @FXML
  private Label lb_playerPoints;

  @FXML
  private Label lb_question;

  @FXML
  private Label lb_questionCounter;

  private List<QuestionModel> questionModels;

  // Methods
  @FXML
  public void loadQuestionFromList(int questionNumber) {
    int qNumber = questionNumber;
    QuestionModel questionModel = questionModels.get(qNumber);
    System.out.println(questionModel);

    lb_question.setText(questionModel.getQuestion());
    btn_A.setText(questionModel.getAnswerA());
    btn_B.setText(questionModel.getAnswerB());
    btn_C.setText(questionModel.getAnswerC());
    btn_D.setText(questionModel.getAnswerD());
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
    this.img_question.setImage(new Image(String.valueOf(
        getClass().getResource("../icons/" + img_path + ".png"))));
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

  public void setQuestionModels(
      List<QuestionModel> questionModels) {
    this.questionModels = questionModels;
  }
}
