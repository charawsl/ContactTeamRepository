package com.yusheng.contactbackend1.service;

import com.yusheng.contactbackend1.pojo.PageBean;
import com.yusheng.contactbackend1.pojo.People;
import com.yusheng.contactbackend1.pojo.phone_number;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PeopleService {



    //查询全部人数据
    List<People> list();

    void delete(Integer id);

    void addPeople(People people);

    PageBean page(Integer page, Integer pageSize, String searchPeopleName, Short searchPeopleGender, Short searchPeopleFavourite);

    People selecetById(Integer id);

    void updatePeople(People people);

    void favouratePeople(Integer id);

    void unfavouratePeople(Integer id);

    void addNumber(Integer id, String number);

    List<phone_number> selectNumberById(Integer id);

    void deleteByNumber(Integer id);
}
