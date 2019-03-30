package com.yuxh.blog.service;

import com.yuxh.blog.model.Type;
import com.yuxh.blog.repository.TypeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeInfoService {

    @Autowired
    private TypeInfoRepository typeInfoRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<Integer, String> findTypeCacheMap() {
        Map<Integer, String> resultMap = redisTemplate.opsForHash().entries("typeMap");
        if (resultMap == null || resultMap.size() < 1) {
            List<Type> list = typeInfoRepository.findAll();
            resultMap = new HashMap<>();
            for (Type type : list) {
                resultMap.put(type.getId(), type.getName());
            }
            redisTemplate.opsForHash().putAll("typeMap", resultMap);
        }
        return resultMap;
    }

    public List<Type> findAll() {
        return typeInfoRepository.findAll();
    }
}
