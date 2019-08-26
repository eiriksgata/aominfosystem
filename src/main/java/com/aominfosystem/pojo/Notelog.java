package com.aominfosystem.pojo;


public class Notelog {

  private long id;
  private String type;
  private java.sql.Timestamp changetime;
  private String content;
  private long changerqq;
  private String changername;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public java.sql.Timestamp getChangetime() {
    return changetime;
  }

  public void setChangetime(java.sql.Timestamp changetime) {
    this.changetime = changetime;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getChangerqq() {
    return changerqq;
  }

  public void setChangerqq(long changerqq) {
    this.changerqq = changerqq;
  }


  public String getChangername() {
    return changername;
  }

  public void setChangername(String changername) {
    this.changername = changername;
  }

}
