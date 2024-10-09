package com.revshop.serviceimpl;

import com.revshop.dto.*;
import com.revshop.model.Users;
import com.revshop.repository.UserRepository;
import com.revshop.service.UserService;

public class UserImpl implements UserService {

    @Override
    public Users registerService(RegisterDTO request) throws Exception {
        // Call the repository to register the user
        return UserRepository.registerRepo(request);
    }
    
    @Override
    public Users loginService(LoginDTO request) throws Exception {
        // Call the repository to authenticate the user
        return UserRepository.loginRepo(request);
    }
}
