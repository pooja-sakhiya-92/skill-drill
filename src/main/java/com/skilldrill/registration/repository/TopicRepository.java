package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Skills;
import com.skilldrill.registration.model.Topic;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, ObjectId> {


    Optional<Topic> findByTopicName(String topicName);

    List<Topic> findTopicBySkills(Skills skills);
}
