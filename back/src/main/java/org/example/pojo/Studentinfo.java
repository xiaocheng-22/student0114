package org.example.pojo;


import com.baomidou.mybatisplus.annotation.TableField;

public class Studentinfo {

  private String id;
  private String number;
  private String name;
  private long age;
  private String sex;
  private String address;
  private String phone;
  private String grade;
  @TableField("classId")
  private String classId;
  @TableField("className")
  private String className;
  private String teacher;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }


  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }


  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }


  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

}
