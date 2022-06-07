package com.example.jplquiz.models;

public record ClientRankingModel(String playerNickName, int playerScore) {

  @Override
  public String toString() {
    return "ClientRankingModel{"
        + "playerNickName='"
        + playerNickName
        + '\''
        + ", playerScore="
        + playerScore
        + '}';
  }
}
