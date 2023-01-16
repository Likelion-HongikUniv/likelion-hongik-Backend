package Likelion.service;

import Likelion.model.entity.Profile;
import Likelion.model.entity.User;


public interface ProfileService {

    public void insertDetail(User user, ProfileDto profileDto);

    public Profile haveUser(int userId);
    public Profile viewProfile(User user);
}

