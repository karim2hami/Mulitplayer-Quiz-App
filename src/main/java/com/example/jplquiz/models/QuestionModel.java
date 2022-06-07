package com.example.jplquiz.models;

import java.io.Serializable;

/**
 * @author karimtouhami QuestionModel: Sets up a new QuestionModel, when the questions are loaded
 * from the .csv file and adds the questionModel objects to a list.
 */
public record QuestionModel(String question, String answerA, String answerB, String answerC,
                            String answerD, String rightAnswer, String imagePath) implements
    Serializable {

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
