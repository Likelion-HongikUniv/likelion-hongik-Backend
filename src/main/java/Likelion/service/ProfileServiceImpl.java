package com.recruit.recruit11.service;

import com.recruit.recruit11.model.Profile;
import com.recruit.recruit11.model.Role;
import com.recruit.recruit11.model.User;
import com.recruit.recruit11.model.UserRepository;
import com.recruit.recruit11.model.dto.ProfileDto;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

//    @Override
//    public void insertDetail(ProfileDto profileDto, User user, String nickname, String major, String studentId, String part, String phoneNum) {
//        ProfileDto.builder()
//                .user(user)
//                .nickname(nickname)
//                .major(major)
//                .studentId(studentId)
//                .part(part)
//                .phoneNum(phoneNum)
//                .build();
//    }


}
