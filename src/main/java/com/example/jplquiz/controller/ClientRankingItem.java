package com.example.jplquiz.controller;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientRankingItem {

  @FXML
  private Label gameScore;

  @FXML
  private Label playerNickName;

  @FXML
  private Label playerRanking;


  public void setGameScore(Label gameScore) {
    this.gameScore = gameScore;
  }

  public void setPlayerNickName(Label playerNickName) {
    this.playerNickName = playerNickName;
  }

  public void setPlayerRanking(Label playerRanking) {
    this.playerRanking = playerRanking;
  }
}
