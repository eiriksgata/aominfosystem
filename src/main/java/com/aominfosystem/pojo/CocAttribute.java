package com.aominfosystem.pojo;


public class CocAttribute {

  private long id;
  private long qq;
  private String attribute;
  private String player;
  private long fromGroup;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getQq() {
    return qq;
  }

  public void setQq(long qq) {
    this.qq = qq;
  }


  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public String getPlayer() {
    return player;
  }

  public long getFromGroup() {
    return fromGroup;
  }

  public void setFromGroup(long fromGroup) {
    this.fromGroup = fromGroup;
  }
}
