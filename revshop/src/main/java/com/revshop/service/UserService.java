package com.revshop.service;

import com.revshop.dto.*;
import com.revshop.model.Users;

public interface UserService {
    Users registerService(RegisterDTO request) throws Exception; // Add method signature for registration
    Users loginService(LoginDTO request) throws Exception; // New method for login
}
