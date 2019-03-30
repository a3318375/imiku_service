package com.yuxh.blog.service;

import com.yuxh.blog.model.Lable;
import com.yuxh.blog.repository.LableInfoRepository;
import com.yuxh.blog.repository.TypeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LableInfoService {

    @Autowired
    private LableInfoRepository lableInfoRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<Integer, String> findLableCacheMap() {
        Map<Integer, String> resultMap = redisTemplate.opsForHash().entries("lableMap");
        if (resultMap == null || resultMap.size() < 1) {
            List<Lable> list = lableInfoRepository.findAll();
            resultMap = new HashMap<>();
            for (Lable lable : list) {
                resultMap.put(lable.getId(), lable.getName());
            }
            redisTemplate.opsForHash().putAll("lableMap", resultMap);
        }
        return resultMap;
    }
}
