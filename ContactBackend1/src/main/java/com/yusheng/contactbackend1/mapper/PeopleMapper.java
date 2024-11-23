package com.yusheng.contactbackend1.mapper;

import com.yusheng.contactbackend1.pojo.People;
import com.yusheng.contactbackend1.pojo.phone_number;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PeopleMapper {
    //查询全部部门数据
    @Select("select * from people")
    public List<People> list();

    //根据id删除部门
    @Delete("delete from people where id = #{id}")
    public void delete(Integer id);

    @Insert("insert into people(name, gender, phone, address, create_time, update_time, favourate) values(#{name},#{gender},#{phone},#{address},#{createTime},#{updateTime}, #{favourate})")
    public void addPeople(People people);

    @Select("select count(*) from people")
    public Long count();

    // @Select("select * from people limit #{start},#{pageSize} where name like")
    public List<People> page(@Param("start") Integer start, @Param("pageSize") Integer pageSize , @Param("searchPeopleName") String searchPeopleName, @Param("searchPeopleGender") Short searchPeopleGender, @Param("searchPeopleFavourite") Short searchPeopleFavourite);

    @Select("select * from people where id = #{id}")
    public People selectById(Integer id);


    public void updatePeople(People people);

    @Update("update people set favourate = '1' where id = #{id}")
    public void favouratePeople(Integer id);

    @Update("update people set favourate = '0' where id = #{id}")
    public void unfavouratePeople(Integer id);


    public void save(List<People> list);

    @Insert("insert into all_phone_number(people_id, number) values(#{id}, #{number})")
    public void addNumber(Integer id, String number);

    @Select("select * from all_phone_number where people_id = #{id}")
    public List<phone_number> selectNumberById(Integer id);

    @Delete("delete from all_phone_number where id = #{id}")
    void deleteByNumber(Integer id);
}
