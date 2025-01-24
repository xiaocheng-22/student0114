/*
mapper 层，也称dao层，是数据持久层，与数据库交互，把数据访问代码和业务逻辑分离开来，增强可维护性
*/
package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Student;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
