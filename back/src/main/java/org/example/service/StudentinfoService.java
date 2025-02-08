package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.pojo.Studentinfo;

import java.util.List;

public interface StudentinfoService {
    boolean saveData(Studentinfo studentinfo);
    List<Studentinfo> getData();
    Studentinfo getDataById(String id);
    List<Studentinfo> getDataByWrapper();
    boolean updateData();
    boolean deleteData();
    boolean deleteDatas();
    IPage<Studentinfo> getDataPage();
    long getDataCount();
    Studentinfo getDataOne();
}
