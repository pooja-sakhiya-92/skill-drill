package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.RatingsDto;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.RatingsMapper;
import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.RatingsRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RatingsServiceImpl implements RatingsService {

    private final RatingsRepository ratingsRepository;

    private final RatingsMapper ratingsMapper;

    private final UserRepository userRepository;

    private final MessageSource messageSource;

    @Autowired
    public RatingsServiceImpl(RatingsRepository ratingsRepository, UserRepository userRepository, RatingsMapper ratingsMapper, MessageSource messageSource) {
        this.ratingsRepository = ratingsRepository;
        this.userRepository = userRepository;
        this.ratingsMapper = ratingsMapper;
        this.messageSource = messageSource;
    }

    @Override
    public RatingsDto addRatings(RatingsDto ratingsDto, String userName) {
        Ratings ratings = ratingsMapper.toRatings(ratingsDto);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));

        ratings.setStars(Integer.valueOf(ratingsDto.getStars()));
        ratings.setUser(user);
        return ratingsMapper.toDto(ratingsRepository.save(ratings));
    }

    @Override
    public RatingsDto updateRatings(String userName, RatingsDto ratingsDto) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Ratings ratings = ratingsRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("No ratings present"));
        ratings.setStars(Integer.valueOf(ratingsDto.getStars()));
        ratings.setComments(ratingsDto.getComments());
        ratingsRepository.save(ratings);
        return ratingsMapper.toDto(ratings);
    }


    @Override
    public void deleteRatings(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Ratings ratings = ratingsRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("No ratings present"));
        ratingsRepository.delete(ratings);
    }

    @Override
    public RatingsDto getAllRatingsOfUser(String userName) {
//        List<Ratings> ratingsList = ratingsRepository.findAll();
//        return ratingsList.stream()
//                .filter(ratings -> ratings.getUser().getEmail().equals(userName))
//                .collect(Collectors.toList());
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Ratings ratings = ratingsRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("No ratings present"));
        return ratingsMapper.toDto(ratings);
    }

    }