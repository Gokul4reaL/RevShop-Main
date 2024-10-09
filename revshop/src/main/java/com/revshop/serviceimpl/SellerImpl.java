package com.revshop.serviceimpl;

import com.revshop.dto.LoginDTO;
import com.revshop.dto.SellerRegisterDTO;
import com.revshop.model.Sellers;
import com.revshop.repository.SellerRepository;
import com.revshop.service.SellerService;

public class SellerImpl implements SellerService {
	 @Override
	    public Sellers registerService(SellerRegisterDTO request) throws Exception {
	        // Call the repository to register the user
	        return SellerRepository.registerRepo(request);
	    }
	    
	    @Override
	    public Sellers loginService(LoginDTO request) throws Exception {
	        // Call the repository to authenticate the user
	        return SellerRepository.loginRepo(request);
	    }
}
