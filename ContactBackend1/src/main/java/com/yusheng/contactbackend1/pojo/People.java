package com.yusheng.contactbackend1.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class People {
    @ExcelIgnore
    private Integer id;
    @ExcelProperty(value = "name", index = 0)
    private String name;
    @ExcelProperty(value = "gender", index = 1)
    private Short gender;
    @ExcelProperty(value = "phone", index = 2)
    private String phone;
    @ExcelProperty(value = "address", index = 3)
    private String address;
    @ExcelIgnore
    private LocalDateTime createTime;
    @ExcelIgnore
    private LocalDateTime updateTime;
    @ExcelIgnore
    private Short favourate;

    public People() {
    }


    public People(Integer id, String name, Short gender, String phone, String address, LocalDateTime createTime, LocalDateTime updateTime, Short favourate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.favourate = favourate;
    }


    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", favourate=" + favourate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Short getFavourate() {
        return favourate;
    }

    public void setFavourate(Short favourate) {
        this.favourate = favourate;
    }
}
