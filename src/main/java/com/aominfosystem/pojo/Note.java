package com.aominfosystem.pojo;


public class Note {

  private long id;
  private String creator;
  private long grade;
  private String title;
  private String hide;
  private String text;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }


  public long getGrade() {
    return grade;
  }

  public void setGrade(long grade) {
    this.grade = grade;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String titile) {
    this.title = titile;
  }


  public String getHide() {
    return hide;
  }

  public void setHide(String hide) {
    this.hide = hide;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
