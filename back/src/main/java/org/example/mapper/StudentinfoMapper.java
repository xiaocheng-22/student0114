package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Studentinfo;

import java.util.ArrayList;

@Mapper
public interface StudentinfoMapper extends BaseMapper<Studentinfo>{
    ArrayList<Object> selectStudentinfo(String name,String classid);
}
