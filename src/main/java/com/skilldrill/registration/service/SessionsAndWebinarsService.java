package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.SessionsAndWebinarsDto;
import com.skilldrill.registration.model.SessionsAndWebinars;

import java.util.List;

public interface SessionsAndWebinarsService {

    SessionsAndWebinars add(SessionsAndWebinarsDto sessionsAndWebinarsDto);

    SessionsAndWebinarsDto update(SessionsAndWebinarsDto sessionsAndWebinarsDto, String id);

    void delete(String id);

    List<SessionsAndWebinars> findAllByTopic(String topicName);
}
