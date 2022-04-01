package com.skilldrill.registration.repository;


import com.skilldrill.registration.model.CreativeArea;
import com.skilldrill.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreativeAreaRepository extends MongoRepository<CreativeArea,String> {
   Optional<CreativeArea> findByCreativeSkill(String creativeSkill);


    List<CreativeArea> findByUser(User user);
}
