package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.BadgesDto;
import com.skilldrill.registration.model.Badges;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BadgeService {

    Badges addBadges(BadgesDto badgesDto, String userName);

    Badges updateIconImage(String userName,String badge,MultipartFile icon);

    BadgesDto updateBadges(String userName,String badge,BadgesDto badgesDto);

    void deleteBadges(String userName,String badge);

    List<Badges> getAllBadgesOfUser(String userName);
}
