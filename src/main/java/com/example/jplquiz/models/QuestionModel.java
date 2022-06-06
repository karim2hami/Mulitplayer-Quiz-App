package com.example.jplquiz.models;

import java.io.Serializable;

/**
 * @author karimtouhami QuestionModel: Sets up a new QuestionModel, when the questions are loaded
 *     from the .csv file and adds the questionModel objects to a list.
 */
public class QuestionModel implements Serializable {

  private String question;
  private String answerA;
  private String answerB;
  private String answerC;
  private String answerD;
  private String rightAnswer;
  private String imagePath;

  public QuestionModel(
      String question,
      String answerA,
      String answerB,
      String answerC,
      String answerD,
      String rightAnswer,
      String imagePath) {
    this.question = question;
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
    this.answerD = answerD;
    this.rightAnswer = rightAnswer;
    this.imagePath = imagePath;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswerA() {
    return answerA;
  }

  public void setAnswerA(String answerA) {
    this.answerA = answerA;
  }

  public String getAnswerB() {
    return answerB;
  }

  public void setAnswerB(String answerB) {
    this.answerB = answerB;
  }

  public String getAnswerC() {
    return answerC;
  }

  public void setAnswerC(String answerC) {
    this.answerC = answerC;
  }

  public String getAnswerD() {
    return answerD;
  }

  public void setAnswerD(String answerD) {
    this.answerD = answerD;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }

  public void setRightAnswer(String rightAnswer) {
    this.rightAnswer = rightAnswer;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public String toString() {
    return "QuestionModel{"
        + "question='"
        + question
        + '\''
        + ", answerA='"
        + answerA
        + '\''
        + ", answerB='"
        + answerB
        + '\''
        + ", answerC='"
        + answerC
        + '\''
        + ", answerD='"
        + answerD
        + '\''
        + ", rightAnswer='"
        + rightAnswer
        + '\''
        + ", imagePath='"
        + imagePath
        + '\''
        + '}';
  }
}
