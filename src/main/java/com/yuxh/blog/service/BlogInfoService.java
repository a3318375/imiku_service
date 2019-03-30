package com.yuxh.blog.service;

import com.yuxh.blog.model.Blog;
import com.yuxh.blog.model.Comment;
import com.yuxh.blog.repository.BlogInfoRepository;
import com.yuxh.blog.repository.CommentInfoRepository;
import com.yuxh.blog.repository.ViewLogRepository;
import com.yuxh.blog.vo.BlogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class BlogInfoService {

    @Autowired
    private BlogInfoRepository blogInfoRepository;

    @Autowired
    private LableInfoService lableInfoService;
    @Autowired
    private TypeInfoService typeInfoService;
    @Autowired
    private CommentInfoRepository commentInfoRepository;
    @Autowired
    private ViewLogRepository viewLogRepository;

    public Page<Blog> findBlogList(Integer pageNumber, Integer pageSize, String title, Integer typeId, Integer topSwitch) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Blog> postIn = blogInfoRepository.findAll((Specification<Blog>) (root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (typeId != null) {
                Predicate namePredicate = cb.equal(root.get("typeId").as(Integer.class), typeId);
                predicatesList.add(namePredicate);
            }
            if (topSwitch != null) {
                Predicate namePredicate = cb.equal(root.get("topSwitch").as(Integer.class), topSwitch);
                predicatesList.add(namePredicate);
            }
            if (StringUtils.isNoneBlank(title)) {
                Predicate namePredicate = cb.like(root.get("blogTitle").as(String.class), "%" + title + "%");
                predicatesList.add(namePredicate);
            }
            query.orderBy(cb.desc(root.get("createDate")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        }, pageable);
        Map<Integer, String> typeMap = typeInfoService.findTypeCacheMap();
        Map<Integer, String> lableMap = lableInfoService.findLableCacheMap();
        Calendar cal = Calendar.getInstance();
        for (Blog blog : postIn.getContent()) {

            if (blog.getLableId() != null) {
                blog.setLableName(typeMap.get(blog.getLableId()));
            }
            if (blog.getTypeId() != null) {
                blog.setTypeName(lableMap.get(blog.getTypeId()));
            }
            cal.setTime(blog.getCreateDate());
            blog.setYear(String.valueOf(cal.get(cal.YEAR)));
            blog.setMonth(String.valueOf(cal.get(cal.MONTH) + 1));
            blog.setDate(String.valueOf(cal.get(cal.DATE)));
            blog.setCommentCount(commentInfoRepository.countByBlogId(blog.getId()));
            blog.setViewCount(viewLogRepository.countByBlogId(blog.getId()));
        }
        return postIn;
    }

    public Blog getById(String blogId) {
        Blog blog = blogInfoRepository.findById(blogId).get();
        if (blog != null) {
            List<Comment> list = commentInfoRepository.findByBlogId(blogId);
            blog.setCommentList(list);
        }
        return blog;
    }
}
