package com.yuxh.blog.vo;

import com.yuxh.blog.model.Blog;
import lombok.Data;

@Data
public class BlogVo extends Blog {

    private String year;
    private String month;
    private String date;
}
