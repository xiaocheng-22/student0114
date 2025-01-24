package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Classinfo;

import java.util.ArrayList;

@Mapper
public interface ClassinfoMapper extends BaseMapper<Classinfo>{
    ArrayList<Object> selectClassinfo(String className);
    ArrayList<Object> selectClassData();
}
