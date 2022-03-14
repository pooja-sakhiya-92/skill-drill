package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.BadgesDto;
import com.skilldrill.registration.enums.BadgeType;
import com.skilldrill.registration.enums.Language;
import com.skilldrill.registration.exceptions.FileStorageException;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.BadgeMapper;
import com.skilldrill.registration.mapper.RatingsMapper;
import com.skilldrill.registration.model.Badges;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.BadgeRepository;
import com.skilldrill.registration.repository.RatingsRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.BadgeService;
import com.skilldrill.registration.utilities.misc.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    private final MessageSource messageSource;

    private final UserRepository userRepository;

    private final RatingsRepository ratingsRepository;

    private final RatingsMapper ratingsMapper;


    @Autowired
    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper, MessageSource messageSource, UserRepository userRepository, RatingsRepository ratingsRepository, RatingsMapper ratingsMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
        this.messageSource = messageSource;
        this.userRepository = userRepository;
        this.ratingsRepository = ratingsRepository;
        this.ratingsMapper = ratingsMapper;
    }

    @Override
    public Badges addBadges(BadgesDto badgesDto, String userName) {
        Badges badges = badgeMapper.toBadges(badgesDto);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        ratingsRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("rating doesn't exist"));
        if(!hasThreeBadge(userName)) {
            badges.setUser(user);

            badges.setBadge(BadgeType.valueOf(badgesDto.getBadge()));
            badges.setBadgeVersion(badgesDto.getBadgeVersion());
            badges.setDescription(badgesDto.getDescription());
            badges.setLanguage(Language.valueOf(badgesDto.getLanguage()));

            LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(2);
            Date twoSecondsLaterAsDate = Date.from(twoSecondsLater.atZone(ZoneId.systemDefault()).toInstant());
            new Timer().schedule(
                    new EmailUtil(userName, "Badge Expired", "your "+badgesDto.getBadge()+" badge is expired."),
                    twoSecondsLaterAsDate);
            badges.setBadgeExpiryDate(String.valueOf(twoSecondsLaterAsDate));
            return badgeRepository.save(badges);
        } else {
            throw new InvalidRequestException("Cant add more than 3 badge");
        }

    }

    public boolean hasThreeBadge(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        List<Badges> badgesList = badgeRepository.findByUser(user);
        return badgesList.size() > 2 ? true : false;
    }

    @Override
    public Badges updateIconImage(String userName, String badge, MultipartFile icon) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Badges badgeDetails = badgeRepository.findByUser(user)
                .stream()
                .filter(badges -> badge.equalsIgnoreCase(String.valueOf(badges.getBadge())))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Badge doesn't exist!"));
        try {
            if (!icon.isEmpty()) {
                badgeDetails.setIcon(icon.getBytes());
            } else {
                throw new NotFoundException("Image not found");
            }
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + icon.getOriginalFilename() + ". Please try again!");
        }

        return badgeRepository.save(badgeDetails);
    }

    @Override
    public BadgesDto updateBadges(String userName, String badge, BadgesDto badgesDto) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Badges badges = badgeRepository.findByUser(user)
                .stream()
                .filter(badgesMock -> badge.equalsIgnoreCase(String.valueOf(badgesMock.getBadge())))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Badge doesn't exist!"));
        badges.setUser(user);
        badges.setBadgeVersion(badgesDto.getBadgeVersion());
        badges.setDescription(badgesDto.getDescription());
        badges.setLanguage(Language.valueOf(badgesDto.getLanguage()));
        badgeRepository.save(badges);
        return badgeMapper.toDto(badges);
    }

    @Override
    public void deleteBadges(String userName, String badge) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        Badges badges = badgeRepository.findByUser(user)
                .stream()
                .filter(badgesMock -> badge.equalsIgnoreCase(String.valueOf(badgesMock.getBadge())))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Badge doesn't exist!"));
        badgeRepository.delete(badges);
    }

    @Override
    public List<Badges> getAllBadgesOfUser(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        return badgeRepository.findByUser(user);
    }

}
