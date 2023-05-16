package com.llqlv.springcourse.controllers;

import com.llqlv.springcourse.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/batch-update")
public class BatchController {

    private final UserDao userDao;

    @Autowired
    public BatchController(UserDao userDao) {
        this.userDao = userDao;
    }


    @GetMapping()
    public String index() {
        return "batch/index";
    }

//    @GetMapping("/without")
//    public String withoutBatch() {
//        userDao.testMultipleUpdate();
//        return "redirect:/people";
//    }
//
//    @GetMapping("/with")
//    public String withBatch() {
//        userDao.testBatchUpdate();
//        return "redirect:/people";
//    }
}
