package com.example.demo.controller;

import com.example.demo.dao.EmpDao;
import com.example.demo.entity.Emp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpController {

    private Logger logger = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpDao empMapper;

    //选择全部用户
    @GetMapping("/selectEmp")
    public String selectEmp(){
        logger.info("com.example.demo.controller.EmpController.selectEmp");
        List<Emp> emps = empMapper.selectEmp();
        for (Emp Emp : emps) {
            System.out.println(Emp);
        }
        return "ok";
    }
    //根据id选择用户
    @GetMapping("/selectEmpById")
    public String selectEmpById(){
        Emp emp = empMapper.selectEmpById(12);
        System.out.println(emp);
        return "ok";
    }
    //添加一个用户
    @GetMapping("/addEmp")
    public String addEmp(){
        empMapper.addEmp(new Emp(1234,"heheda"));
        return "ok";
    }
    //修改一个用户
    @GetMapping("/updateEmp")
    public String updateEmp(){
        empMapper.updateEmp(new Emp(1234,"heihei"));
        return "ok";
    }
    //根据id删除用户
    @GetMapping("/deleteEmp")
    public String deleteEmp(){
        empMapper.deleteEmp(1234);
        return "ok";
    }
}

