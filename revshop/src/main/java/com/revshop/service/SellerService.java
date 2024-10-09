package com.revshop.service;

import com.revshop.dto.LoginDTO;
import com.revshop.dto.SellerRegisterDTO;
import com.revshop.model.Sellers;

public interface SellerService {
	 Sellers registerService(SellerRegisterDTO request) throws Exception; // Add method signature for registration
	 Sellers loginService(LoginDTO request) throws Exception; // New method for login
}
