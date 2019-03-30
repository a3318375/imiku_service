package com.yuxh.blog.controller;

import com.yuxh.blog.contact.BaseJsonResult;
import com.yuxh.blog.model.Type;
import com.yuxh.blog.service.TypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeInfoController {

    @Autowired
    private TypeInfoService typeInfoService;

    @RequestMapping("/list")
    public BaseJsonResult list() {
        List<Type> list = typeInfoService.findAll();
        return BaseJsonResult.success("list", list);
    }
}
