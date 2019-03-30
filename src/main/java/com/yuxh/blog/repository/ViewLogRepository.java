package com.yuxh.blog.repository;

import com.yuxh.blog.model.ViewLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewLogRepository extends JpaRepository<ViewLog, Integer> {

    int countByBlogId(String id);

}
