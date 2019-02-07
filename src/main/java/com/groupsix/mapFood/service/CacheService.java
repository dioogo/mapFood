package com.groupsix.mapFood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;
import com.groupsix.mapFood.entity.CacheEntity;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.repository.CacheRepository;



@Service
public class CacheService {

	private static final int CACHE_ID = 1;
	
	@Autowired
	private CacheRepository cacheRepository;
	
	public List<CacheMotoboy> getCache() {
		CacheEntity entity = cacheRepository.findById(CACHE_ID).get();
		
		Type listType = new TypeToken<ArrayList<CacheMotoboy>>(){}.getType();
		return new Gson().fromJson(entity.getData(), listType);
	}
}
