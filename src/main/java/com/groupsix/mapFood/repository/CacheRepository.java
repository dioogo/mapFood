package com.groupsix.mapFood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.entity.CacheEntity;

@Repository
public interface CacheRepository extends CrudRepository<CacheEntity, Integer> {

}
