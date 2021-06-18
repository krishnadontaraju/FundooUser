package com.fundoo.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.registration.dto.FundooUserDTO;
import com.fundoo.registration.dto.ResponseDTO;
import com.fundoo.registration.exception.FundooUserException;
import com.fundoo.registration.service.IFundooUserService;

@RestController
@RequestMapping("/fundoo/users")
public class FundooRegistrationController {
	
	@Autowired
	private IFundooUserService fundooUserService;

	//End point that returns users : default end point
	@GetMapping(value = {"" , "/" , "/users"})
	public ResponseEntity<ResponseDTO> getAllUsers(){
		
		ResponseDTO responseDTO = fundooUserService.viewAllUsers();
		return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
	}
	
	//End point to register User
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> registerUsers(@RequestBody FundooUserDTO userDTO ) throws FundooUserException{

		ResponseDTO responseDTO = fundooUserService.registerUser(userDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
	}
	
	//End point to update User
	@PutMapping("/update/{userID}")
	public ResponseEntity<ResponseDTO> updateUsers(@PathVariable("userID") int userID ,@RequestBody FundooUserDTO userDTO) throws FundooUserException{
    	
    	ResponseDTO responseDTO = fundooUserService.updateUser(userID, userDTO);
        return new ResponseEntity<ResponseDTO>( responseDTO , HttpStatus.OK);
    }
	
	//End point to delete User 
	@DeleteMapping("/delete/{userID}")
	public ResponseEntity<ResponseDTO> deleteUserData(@PathVariable("userID") int userID) throws FundooUserException{
		
    	ResponseDTO responseDTO = fundooUserService.deleteUser(userID);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }
	
	//End point to verify User
	@PutMapping("/verify/{token}")
	public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token ) throws FundooUserException{
		
		ResponseDTO verifyUserResponse = fundooUserService.verifyUser(token);
		
		return new ResponseEntity<ResponseDTO>(verifyUserResponse , HttpStatus.OK);
		
	}
	
	//End point for Notes service to check for user
	@GetMapping("/checkUser/{userId}")
	public boolean checkForThePresenceOfUser(@PathVariable long userId) {
	
		return fundooUserService.checkUser(userId);
		
	}
	
	//End point for Notes service to check for email
	@GetMapping("/checkEmailId/{emailId}")
	public boolean checkIfEmailIdIsPresentOrNot(@PathVariable String emailId) {
		
		return fundooUserService.checkIfEmailIsPresent(emailId);
	}
	
	//End point for Notes service to check for user by email
	@GetMapping("/getUserWithEmailId/{emailId}")
	public Long checkIfEmailIdIsPresentOrNotAndReturnUser(@PathVariable String emailId) {
		
		return fundooUserService.checkIfUserPresentThenReturnUser(emailId);
	}
	
}
