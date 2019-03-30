package com.yuxh.blog.controller;

import com.yuxh.blog.contact.BaseJsonResult;
import com.yuxh.blog.model.Blog;
import com.yuxh.blog.service.BlogInfoService;
import com.yuxh.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;

    @RequestMapping("/list/{pageNumber}/{pageSize}")
    public BaseJsonResult findBlogList(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
                                       String title, Integer typeId, Integer topSwitch) {
        Page<Blog> page = blogInfoService.findBlogList(pageNumber, pageSize, title, typeId, topSwitch);
        return BaseJsonResult.success("page", page);
    }

    @RequestMapping("/{blogId}")
    public BaseJsonResult getById(@PathVariable String blogId) {
        Blog blog = blogInfoService.getById(blogId);
        return BaseJsonResult.success("info", blog);
    }
}
