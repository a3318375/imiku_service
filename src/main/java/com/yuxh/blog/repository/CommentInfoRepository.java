package com.yuxh.blog.repository;

import com.yuxh.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentInfoRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBlogId(String blogId);

    int countByBlogId(String blogId);

}
