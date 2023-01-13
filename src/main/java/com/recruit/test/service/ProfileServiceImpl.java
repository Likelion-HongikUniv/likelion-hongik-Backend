package com.recruit.test.service;

import com.recruit.test.model.Profile;
import com.recruit.test.model.User;
import com.recruit.test.model.UserRepository;
import com.recruit.test.model.dto.ProfileDto;
import com.recruit.test.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Override
    public void insertDetail(User user, ProfileDto profileDto) {
        Profile profile = profileDto.toEntity(user);
        profileRepository.save(profile);
        System.out.println("profile save 완료");
    }

    @Override
    public Profile viewProfile(User user){
        Profile profile = profileRepository.findById(user.getId());
        return profile;
    }
}
