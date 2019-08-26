package com.aominfosystem.pojo;


public class User {

  private long id;
  private long fromqq;
  private String qqname;
  private long grade;
  private String nickname;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getFromqq() {
    return fromqq;
  }

  public void setFromqq(long fromqq) {
    this.fromqq = fromqq;
  }


  public String getQqname() {
    return qqname;
  }

  public void setQqname(String qqname) {
    this.qqname = qqname;
  }


  public long getGrade() {
    return grade;
  }

  public void setGrade(long grade) {
    this.grade = grade;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

}
