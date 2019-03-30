package com.yuxh.blog.repository;

import com.yuxh.blog.model.Blog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogInfoRepository extends JpaRepository<Blog, String> {

    Page<Blog> findAll(Specification<Blog> spec, Pageable pageable);

}
