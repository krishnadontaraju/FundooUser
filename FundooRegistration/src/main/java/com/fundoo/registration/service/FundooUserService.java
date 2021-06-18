package com.fundoo.registration.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	@Autowired
	private JavaMailSender mailSender;
	
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
			//Sending the Verification Email
			sendEmail(userDTO.emailId , "Verify your Email" , tokenManager.createToken(note.getId()));
			log.info("note suucessfully added User with First Name "+userDTO.fname);
			ResponseDTO registerUserRespone = new ResponseDTO("Successfully added User ", tokenManager.createToken(note.getId()));
			
			return registerUserRespone;
		}else {
			throw new FundooUserException(560 , "Email Id has already been registered");
		}
		
		
		
		//Send a mail to email id and attach the verification link which has the token 
		//the token shall contain a encrypted  message
	}

	//Method to Update User details
	@Override
	public ResponseDTO updateUser(long userID, FundooUserDTO userDTO) throws FundooUserException {
		log.info("Accessed to Update User");
		Optional<FundooUser> probableUser = fundooUserRepository.findById(userID);
		//Checking if User is Present or not
		if (probableUser.isPresent()) {
			log.info("User found now initiating the updation");
			probableUser.get().updateUser(userDTO);;
			
			fundooUserRepository.save(probableUser.get());
			
			ResponseDTO updateUserResponse = new ResponseDTO("Successfully changed the User " , probableUser.get());
			log.info("Succesfully updated the user to " +userDTO+" for User ID "+userID);
			return updateUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}

	//Method that handles removal of User
	@Override
	public ResponseDTO deleteUser(long userID) throws FundooUserException {
		log.info("Accessed to Update User");
		Optional<FundooUser> probableUser = fundooUserRepository.findById(userID);
		//Checking if User is Present or not
		if (probableUser.isPresent()) {
			log.info("User found now initiating the deltion for "+probableUser.get());
			ResponseDTO deleteUserResponse = new ResponseDTO("Successfully deleted the User " , probableUser.get());
			
			fundooUserRepository.delete(probableUser.get());
			log.info("User has been delted");
			return deleteUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}

	//This method is used verify the User the parameter is a token which is recieved from the Email li n k which sent 
	//During the registration
	@Override
	public ResponseDTO verifyUser(String token) throws FundooUserException {
		log.info("Accessed to Update User");
		long id = tokenManager.decodeToken(token);
				
		Optional<FundooUser> probableUser = fundooUserRepository.findById(id);
		
		if (probableUser.isPresent()) {
			log.info("User found now initiating the verification");
			probableUser.get().setVerified(true);
			fundooUserRepository.save(probableUser.get());
			ResponseDTO verifyUserResponse = new ResponseDTO("Successfully verified the user " , probableUser.get().getEmailId());
			log.info("User has been Verified");
			return verifyUserResponse;
		}else {
			throw new FundooUserException(501 , "User not found");
		}
	}
	
	//Method to send a Verification email to the user
	private void sendEmail(String reciever , String subject , String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(reciever);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		
	}

	//This method is to handle the request sent by the Notes service
	//Returns true if user is found
	@Override
	public boolean checkUser(long userId) {
		log.info("Accessed to check User by Notes Service");
		Optional<FundooUser> isUSerPresent = fundooUserRepository.findById(userId);
		
		if (isUSerPresent.isEmpty()) {
			log.info("User was Found "+isUSerPresent.get());
			return false;
		}
		log.info("User  not found with Id "+userId);
		return true;
		
	}

	//This method is to handle the request sent by the Notes service
	//Returns true if email found
	@Override
	public boolean checkIfEmailIsPresent(String emailId) {
		log.info("Accessed to check User by Email requested by Notes Service");
		Optional<FundooUser> isEmailPresent = fundooUserRepository.findByEmailId(emailId);
		
		if (isEmailPresent.isEmpty()) {
			log.info("User was Found "+isEmailPresent.get());
			return false;
		}
		log.info("User  not found with EmailId "+emailId);
		return true;
	}

	//This method is to handle the request sent by the Notes service
	//Returns the User if found
	@Override
	public Long checkIfUserPresentThenReturnUser(String emailId) {
		log.info("Accessed to check and Return User by Notes Service");
		Optional<FundooUser> isEmailPresent = fundooUserRepository.findByEmailId(emailId);
		
		if (isEmailPresent.isPresent()) {
			log.info("User was Found "+isEmailPresent.get());
			return isEmailPresent.get().getId();
		}
		log.info("User  not found with EmailId "+emailId);
		return null;
	}

}
