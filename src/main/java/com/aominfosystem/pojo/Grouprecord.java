package com.aominfosystem.pojo;


public class Grouprecord {

  private long id;
  private long msgid;
  private long fromgroup;
  private long fromqq;
  private String fromanonymous;
  private String msg;
  private java.sql.Timestamp time;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getMsgid() {
    return msgid;
  }

  public void setMsgid(long msgid) {
    this.msgid = msgid;
  }


  public long getFromgroup() {
    return fromgroup;
  }

  public void setFromgroup(long fromgroup) {
    this.fromgroup = fromgroup;
  }


  public long getFromqq() {
    return fromqq;
  }

  public void setFromqq(long fromqq) {
    this.fromqq = fromqq;
  }


  public String getFromanonymous() {
    return fromanonymous;
  }

  public void setFromanonymous(String fromanonymous) {
    this.fromanonymous = fromanonymous;
  }


  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
