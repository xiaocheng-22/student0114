package org.example.pojo;

import org.apache.ibatis.type.Alias;
//别名
@Alias("Classinfo")
public class Classinfo {

  private String id;
  private String parentId;
  private String className;
  private String teacher;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
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
