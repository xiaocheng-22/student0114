package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.example.mapper.StudentinfoMapper;
import org.example.pojo.Studentinfo;
import org.example.service.StudentinfoService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentinfoServiceImpl extends ServiceImpl<StudentinfoMapper,Studentinfo> implements StudentinfoService {
    //新增一条数据
    @Override
    public boolean saveData(Studentinfo studentinfo){
        return this.save(studentinfo);
    }
    //查询所有记录
    @Override
    public List<Studentinfo>  getData(){
        List<Studentinfo> studentinfo=this.list();
        return studentinfo;
    }
    //根据ID查询记录
    @Override
    public Studentinfo getDataById(String id){
        Studentinfo studentinfo=this.getById(id);
        return studentinfo;
    }
    //根据条件查询记录
    @Override
    public List<Studentinfo> getDataByWrapper(){
        QueryWrapper<Studentinfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name","A");
        List<Studentinfo> studentinfo=this.list(queryWrapper);
        return studentinfo;
    }
    //根据 ID 更新记录
    @Override
    public boolean updateData(){
        Studentinfo studentinfo=this.getDataById("1887771699008933889");
        studentinfo.setAge(13);
        this.updateById(studentinfo);
        return this.updateById(studentinfo);
    }
    //根据 ID 删除记录
    @Override
    public boolean deleteData(){
        return this.removeById("1888035133403340802");
    }
    //批量删除记录
    @Override
    public boolean deleteDatas(){
        //如果已经知道列表中的元素，可以使用 `Arrays.asList` 方法来声明和初始化。列表是固定大小的，不能添加或删除元素。
        List<String> ids= Arrays.asList("1887771774514794497","1887774827263438850");
        return this.removeByIds(ids);
    }
    //分页查询记录
    @Override
    public IPage<Studentinfo> getDataPage(){
        //Page 类用于设置分页参数
        Page<Studentinfo> page=new Page<>(2,2);
        //如果有查询条件
        QueryWrapper<Studentinfo> queryWrapper = new QueryWrapper<>();
        //IPage 用于封装分页结果
        IPage<Studentinfo> studentPage=this.page(page,null);
        return studentPage;
    }
    //根据条件统计记录数量
    @Override
    public long getDataCount(){
        QueryWrapper<Studentinfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","张");
        long count=this.count(queryWrapper);
        return count;
    }
    //根据条件查询单个记录
    /*getOne 方法是 MyBatis-Plus 提供的一个便捷方法，用于查询 单条记录。它的默认行为是：
    如果查询结果有多条记录，会抛出 TooManyResultsException 异常。
    如果查询结果为空，则返回 null。

    为什么会报错？
    getOne 方法的语义是查询 唯一一条记录。如果查询条件匹配到多条记录，MyBatis-Plus 无法确定应该返回哪一条，因此会抛出异常。

    如果你希望在有多个匹配记录时只返回其中一条，而不是抛出异常，可以使用 getOne 方法的重载版本，并传入 false 参数

    参数说明：throwEx：是否抛出异常。
    true：如果查询到多条记录，抛出 TooManyResultsException（默认行为）。
    false：如果查询到多条记录，只返回第一条，不抛出异常。*/
    @Override
    public Studentinfo getDataOne(){
        QueryWrapper<Studentinfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name","A");
        Studentinfo studentinfo=this.getOne(queryWrapper, false);
        return studentinfo;
    }





}
