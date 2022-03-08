package com.example.jplquiz.models;

public class NickNameModel {

  private String nickName;

  public NickNameModel(String nickName) {
    this.nickName = nickName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @Override
  public String toString() {
    return "NickNameModel{" + "nickName='" + nickName + '\'' + '}';
  }
}
