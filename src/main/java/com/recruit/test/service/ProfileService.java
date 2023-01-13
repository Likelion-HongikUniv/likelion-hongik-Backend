package com.recruit.test.service;

import com.recruit.test.model.Profile;
import com.recruit.test.model.User;
import com.recruit.test.model.dto.ProfileDto;

public interface ProfileService {

    public void insertDetail(User user, ProfileDto profileDto);

    public Profile haveUser(int userId);
    public Profile viewProfile(User user);
}
