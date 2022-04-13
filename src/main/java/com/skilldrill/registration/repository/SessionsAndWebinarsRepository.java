package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.SessionsAndWebinars;
import com.skilldrill.registration.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SessionsAndWebinarsRepository extends MongoRepository<SessionsAndWebinars, String> {
    List<SessionsAndWebinars> findByTopic(Topic topic);
}
