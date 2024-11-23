package com.yusheng.contactbackend1.service.impl;

import com.yusheng.contactbackend1.mapper.PeopleMapper;
import com.yusheng.contactbackend1.pojo.PageBean;
import com.yusheng.contactbackend1.pojo.People;
import com.yusheng.contactbackend1.pojo.phone_number;
import com.yusheng.contactbackend1.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;


    @Override
    public List<People> list() {
        return peopleMapper.list();
    }


    //根据id删除部门
    @Override
    public void delete(Integer id) {
        peopleMapper.delete(id);
    }

    @Override
    public void addPeople(People people) {
        people.setFavourate((short) 0);
        people.setCreateTime(LocalDateTime.now());
        people.setUpdateTime(LocalDateTime.now());
        peopleMapper.addPeople(people);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String searchPeopleName, Short searchPeopleGender, Short searchPeopleFavourite) {

        Long count = peopleMapper.count();
        Integer start = (page - 1) * pageSize;
        List<People> listPeople = peopleMapper.page(start, pageSize, searchPeopleName, searchPeopleGender, searchPeopleFavourite);

        return new PageBean(count, listPeople);
    }

    @Override
    public People selecetById(Integer id) {
        People people = peopleMapper.selectById(id);
        return people;
    }

    @Override
    public void updatePeople(People people) {
        people.setUpdateTime(LocalDateTime.now());
        peopleMapper.updatePeople(people);
    }

    @Override
    public void favouratePeople(Integer id) {
        peopleMapper.favouratePeople(id);
    }

    @Override
    public void unfavouratePeople(Integer id) {
        peopleMapper.unfavouratePeople(id);
    }

    @Override
    public void addNumber(Integer id, String number) {
        peopleMapper.addNumber(id, number);
    }

    @Override
    public List<phone_number> selectNumberById(Integer id) {
        List<phone_number> numberList = peopleMapper.selectNumberById(id);
        return numberList;
    }

    @Override
    public void deleteByNumber(Integer id) {
        peopleMapper.deleteByNumber(id);
    }
}
