package com.example.jplquiz.models;

/**
 * @author karimtouhami NickNameModel: Sets up a new NickNameModel, which is assigned to the
 *     NickNameItem Controller class.
 */
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
