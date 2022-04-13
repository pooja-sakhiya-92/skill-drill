package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.SessionsAndWebinarsDto;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.SessionsAndWebinarsMapper;
import com.skilldrill.registration.model.SessionsAndWebinars;
import com.skilldrill.registration.model.Topic;
import com.skilldrill.registration.repository.SessionsAndWebinarsRepository;
import com.skilldrill.registration.repository.TopicRepository;
import com.skilldrill.registration.service.SessionsAndWebinarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class SessionsAndWebinarsServiceImpl implements SessionsAndWebinarsService {

    @Autowired
    SessionsAndWebinarsRepository sessionsAndWebinarsRepository;

    @Autowired
    SessionsAndWebinarsMapper sessionsAndWebinarsMapper;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MessageSource messageSource;

    @Override
    public SessionsAndWebinars add(SessionsAndWebinarsDto sessionsAndWebinarsDto) {
        SessionsAndWebinars sessionsAndWebinars = sessionsAndWebinarsMapper.toSessionsAndWebinars(sessionsAndWebinarsDto);
        Topic topic = topicRepository.findByTopicName(sessionsAndWebinarsDto.getTopic().getTopicName())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("topic.not.found",
                        null, MessageSourceAlternateResource.TOPICS_NOT_FOUND, Locale.ENGLISH)));
        sessionsAndWebinars.setDateOfSession(String.valueOf(LocalDate.now()));
        sessionsAndWebinars.setTopic(topic);
        return sessionsAndWebinarsRepository.save(sessionsAndWebinars);

    }

    @Override
    public SessionsAndWebinarsDto update(SessionsAndWebinarsDto sessionsAndWebinarsDto, String id) {
        Topic topic = topicRepository.findByTopicName(sessionsAndWebinarsDto.getTopic().getTopicName())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("topic.not.found",
                        null, MessageSourceAlternateResource.TOPICS_NOT_FOUND, Locale.ENGLISH)));
        SessionsAndWebinars sessionsAndWebinars = sessionsAndWebinarsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No id found"));
        sessionsAndWebinars.setLink(sessionsAndWebinarsDto.getLink());
        sessionsAndWebinars.setNote(sessionsAndWebinarsDto.getNote());
        sessionsAndWebinars.setStartTime(sessionsAndWebinarsDto.getStartTime());
        sessionsAndWebinars.setDuration(sessionsAndWebinarsDto.getDuration());
        sessionsAndWebinars.setDateOfSession(String.valueOf(LocalDate.now()));
        sessionsAndWebinars.setTopic(topic);
        sessionsAndWebinarsRepository.save(sessionsAndWebinars);
        return sessionsAndWebinarsMapper.toDto(sessionsAndWebinars);
    }

    @Override
    public void delete(String id) {
        SessionsAndWebinars sessionsAndWebinars = sessionsAndWebinarsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("session or webinar id not found"));
        sessionsAndWebinarsRepository.delete(sessionsAndWebinars);

    }

    @Override
    public List<SessionsAndWebinars> findAllByTopic(String topicName) {
        Topic topic = topicRepository.findByTopicName(topicName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("topic.not.found",
                        null, MessageSourceAlternateResource.TOPICS_NOT_FOUND, Locale.ENGLISH)));
        return sessionsAndWebinarsRepository.findByTopic(topic);
    }
}
