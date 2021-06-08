package com.fundoo.registration.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoo.registration.dto.FundooUserDTO;
import com.fundoo.registration.dto.ResponseDTO;
import com.fundoo.registration.exception.FundooUserException;
import com.fundoo.registration.model.FundooUser;
import com.fundoo.registration.repository.FundooUserRepository;
import com.fundoo.registration.util.TokenUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FundooUserService implements IFundooUserService{

	@Autowired
	private FundooUserRepository fundooUserRepository;
	
	@Autowired
	private TokenUtility tokenManager;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ResponseDTO viewAllUsers() {
		
		ResponseDTO viewAllUsersResponse = new ResponseDTO("Succesfully fetched the Users" , fundooUserRepository.findAll());
		
		return viewAllUsersResponse;
	}

	@Override
	public ResponseDTO registerUser(FundooUserDTO userDTO) throws FundooUserException {
		
		Optional<FundooUser> probableUser = fundooUserRepository.findByEmailId(userDTO.emailId);
		
		if (probableUser.isEmpty()) {
			log.info("Requested User Addition");
			//Mapping the parameter to the Model
			FundooUser note = mapper.map(userDTO , FundooUser.class);
			fundooUserRepository.save(note);
			log.info("note suucessfully added User with First Name "+userDTO.fname);
			ResponseDTO registerUserRespone = new ResponseDTO("Successfully added User ", tokenManager.createToken(note.getId()));
			
			return registerUserRespone;
		}else {
			throw new FundooUserException(560 , "Email Id has already been registered");
		}
		
		
		
		//Send a mail to email id and attach the verification link which has the token 
		//the token shall contain a crypted  message
	}

	@Override
	public ResponseDTO updateEmployeePayRollData(long userID, FundooUserDTO userDTO) throws FundooUserException {
		
		Optional<FundooUser> probableUser = fundooUserRepository.findById(userID);
		
		if (probableUser.isPresent()) {
			probableUser.get().updateUser(userDTO);;
			
			fundooUserRepository.save(probableUser.get());
			
			ResponseDTO updateUserResponse = new ResponseDTO("Successfully changed the User " , probableUser.get());
			
			return updateUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}

	@Override
	public ResponseDTO deleteEmployeePayRollData(long userID) throws FundooUserException {
		
		Optional<FundooUser> probableUser = fundooUserRepository.findById(userID);
		
		if (probableUser.isPresent()) {
			
			ResponseDTO deleteUserResponse = new ResponseDTO("Successfully deleted the User " , probableUser.get());
			
			fundooUserRepository.delete(probableUser.get());
			
			return deleteUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}

	@Override
	public ResponseDTO verifyUser(String token) throws FundooUserException {
		
		long id = tokenManager.decodeToken(token);
				
		Optional<FundooUser> probableUser = fundooUserRepository.findById(id);
		
		if (probableUser.isPresent()) {
			
			probableUser.get().setVerified(true);
			
			fundooUserRepository.save(probableUser.get());
			
			ResponseDTO verifyUserResponse = new ResponseDTO("Successfully verified the user " , probableUser.get().getEmailId());
			
			return verifyUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}

}
