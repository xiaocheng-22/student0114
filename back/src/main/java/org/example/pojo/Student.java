/*
pojo层 也称entity层，用于存放实体类的地方，里面新建了一个student实体类，
里面的属性要和数据库中的属性值和类型一一对应
通过最右侧的 数据库--脚本扩展 可自动生成
*/
package org.example.pojo;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.UUID;
@TableName("student") // 对应数据库中的表名
public class Student {
  @TableId(value = "id", type = IdType.ASSIGN_UUID) // 设置主键为 UUID
  private String id;
  private String number;
  private String name;
  private long age;
  private long chi;
  private long math;
  private long eng;

  public Student() {
    this.id = UUID.randomUUID().toString(); // 在构造函数中生成 UUID
  }

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


  public long getChi() {
    return chi;
  }

  public void setChi(long chi) {
    this.chi = chi;
  }


  public long getMath() {
    return math;
  }

  public void setMath(long math) {
    this.math = math;
  }


  public long getEng() {
    return eng;
  }

  public void setEng(long eng) {
    this.eng = eng;
  }

}
