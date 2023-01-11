package com.recruit.test.service;

import com.recruit.test.model.UserRepository;
import lombok.RequiredArgsConstructor;

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
