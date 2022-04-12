package com.example.jplquiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  public Button getBtn_A() {
    return btn_A;
  }

  public void setBtn_A(Button btn_A) {
    this.btn_A = btn_A;
  }

  public Button getBtn_B() {
    return btn_B;
  }

  public void setBtn_B(Button btn_B) {
    this.btn_B = btn_B;
  }

  public Button getBtn_C() {
    return btn_C;
  }

  public void setBtn_C(Button btn_C) {
    this.btn_C = btn_C;
  }

  public Button getBtn_D() {
    return btn_D;
  }

  public void setBtn_D(Button btn_D) {
    this.btn_D = btn_D;
  }

  public ImageView getImg_question() {
    return img_question;
  }

  public void setImg_question(ImageView img_question) {
    this.img_question = img_question;
  }

  public Label getLb_countDown() {
    return lb_countDown;
  }

  public void setLb_countDown(Label lb_countDown) {
    this.lb_countDown = lb_countDown;
  }

  public Label getLb_playerPoints() {
    return lb_playerPoints;
  }

  public void setLb_playerPoints(Label lb_playerPoints) {
    this.lb_playerPoints = lb_playerPoints;
  }

  public Label getLb_question() {
    return lb_question;
  }

  public void setLb_question(Label lb_question) {
    this.lb_question = lb_question;
  }

  public Label getLb_questionCounter() {
    return lb_questionCounter;
  }

  public void setLb_questionCounter(Label lb_questionCounter) {
    this.lb_questionCounter = lb_questionCounter;
  }
}
