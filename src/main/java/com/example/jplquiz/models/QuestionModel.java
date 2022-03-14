package com.example.jplquiz.models;

public class QuestionModel {

  private String questionA;
  private String questionB;
  private String questionC;
  private String questionD;
  private String rightAnswer;

  public QuestionModel(
      String questionA, String questionB, String questionC, String questionD, String rightAnswer) {
    this.questionA = questionA;
    this.questionB = questionB;
    this.questionC = questionC;
    this.questionD = questionD;
    this.rightAnswer = rightAnswer;
  }

  public String getQuestionA() {
    return questionA;
  }

  public void setQuestionA(String questionA) {
    this.questionA = questionA;
  }

  public String getQuestionB() {
    return questionB;
  }

  public void setQuestionB(String questionB) {
    this.questionB = questionB;
  }

  public String getQuestionC() {
    return questionC;
  }

  public void setQuestionC(String questionC) {
    this.questionC = questionC;
  }

  public String getQuestionD() {
    return questionD;
  }

  public void setQuestionD(String questionD) {
    this.questionD = questionD;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }

  public void setRightAnswer(String rightAnswer) {
    this.rightAnswer = rightAnswer;
  }

  @Override
  public String toString() {
    return "QuestionModel{"
        + "questionA='"
        + questionA
        + '\''
        + ", questionB='"
        + questionB
        + '\''
        + ", questionC='"
        + questionC
        + '\''
        + ", questionD='"
        + questionD
        + '\''
        + ", rightAnswer='"
        + rightAnswer
        + '\''
        + '}';
  }
}
