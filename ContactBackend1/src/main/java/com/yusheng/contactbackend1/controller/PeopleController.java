package com.yusheng.contactbackend1.controller;

import com.alibaba.excel.EasyExcel;
import com.yusheng.contactbackend1.pojo.PageBean;
import com.yusheng.contactbackend1.pojo.People;
import com.yusheng.contactbackend1.pojo.Result;
import com.yusheng.contactbackend1.pojo.phone_number;
import com.yusheng.contactbackend1.service.PeopleService;
import com.yusheng.contactbackend1.utils.EasyExcelUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin
@RestController
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    //查询所有人
    @GetMapping("/listPeople")
    public Result list(){

        System.out.println("查询全部人数据");

        List<People> peopleList = peopleService.list();
        return Result.success(peopleList);
    }

    //根据id删除
    @DeleteMapping("/listPeople/{id}")
    public Result delete(@PathVariable Integer id){

        peopleService.delete(id);
        return Result.success();
    }

    //添加人
    @PostMapping("/listPeople")
    public Result addPeople(@RequestBody People people)
    {

        peopleService.addPeople(people);
        return Result.success();
    }

    //修改人
    @PostMapping("/updatePeople")
    public Result updatePeople(@RequestBody People people)
    {

        peopleService.updatePeople(people);
        return Result.success();
    }

    @GetMapping("/pageListPeople")
    public Result page(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10")Integer pageSize, String searchPeopleName, Short searchPeopleGender, Short searchPeopleFavourite){

        PageBean pageBean = peopleService.page(page, pageSize, searchPeopleName, searchPeopleGender, searchPeopleFavourite);
        return Result.success(pageBean);
    }

        @GetMapping("/selectById/{id}")
        public Result selectById(@PathVariable Integer id){//不写注释会报错

            People people = peopleService.selecetById(id);
            return Result.success(people);
        }


    //喜欢人
    @GetMapping("/favouritePeople/{id}")
    public Result favouritePeople(@PathVariable Integer id)
    {

        peopleService.favouratePeople(id);
        return Result.success();
    }

    //不喜欢人
    @GetMapping("/unfavouritePeople/{id}")
    public Result unfavouritePeople(@PathVariable Integer id)
    {

        peopleService.unfavouratePeople(id);
        return Result.success();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {

        List<People> userInfoList = peopleService.list();
        EasyExcelUtils.download("用户列表", userInfoList);



    }

    @PostMapping("/import")
    public Result userInfoExcelImport(@RequestParam("file") MultipartFile file) throws IOException {

        List<People> userInfoList = EasyExcelUtils.readExcel(People.class, file.getInputStream());

        for (People people : userInfoList) {
            peopleService.addPeople(people);
        }
        return Result.success();
    }

    @GetMapping("/addNumber/{id}/{number}")
    public Result addNumber(@PathVariable Integer id, @PathVariable String number){
        peopleService.addNumber(id, number);
        return Result.success();
    }

    @GetMapping("selectNumberById/{id}")
    public Result selectNumberById(@PathVariable Integer id){
        List<phone_number> numberList = peopleService.selectNumberById(id);
        return Result.success(numberList);
    }

    @GetMapping("deleteByNumber/{id}")
    public Result deleteByNumber(@PathVariable Integer id){
        System.out.println(id);
        peopleService.deleteByNumber(id);
        return Result.success();
    }



}
