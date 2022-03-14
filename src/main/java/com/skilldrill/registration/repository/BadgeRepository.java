package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Badges;
import com.skilldrill.registration.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends MongoRepository<Badges, ObjectId> {
//    @Query("{$and:[{user:?0},{badge:?1}]}")
    List<Badges> findByUser(User user);
}
