package com.example.jplquiz.models;

public class ClientRankingModel {

  private String playerNickName;
  private int playerScore;

  public ClientRankingModel(String playerNickName, int playerScore) {
    this.playerNickName = playerNickName;
    this.playerScore = playerScore;
  }

  public String getPlayerNickName() {
    return playerNickName;
  }

  public void setPlayerNickName(String playerNickName) {
    this.playerNickName = playerNickName;
  }

  public int getPlayerScore() {
    return playerScore;
  }

  public void setPlayerScore(int playerScore) {
    this.playerScore = playerScore;
  }



  @Override
  public String toString() {
    return "ClientRankingModel{" +
        "playerNickName='" + playerNickName + '\'' +
        ", playerScore=" + playerScore +
        '}';
  }

  public int compareTo(ClientRankingModel clientRankingModel) {
    return Integer.compare(this.playerScore, clientRankingModel.playerScore);
  }
}
