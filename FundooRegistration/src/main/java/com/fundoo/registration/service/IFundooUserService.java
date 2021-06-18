package com.fundoo.registration.service;

import com.fundoo.registration.dto.FundooUserDTO;
import com.fundoo.registration.dto.ResponseDTO;
import com.fundoo.registration.exception.FundooUserException;

public interface IFundooUserService {

	ResponseDTO viewAllUsers();

	ResponseDTO registerUser(FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO updateUser(long userID, FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO deleteUser(long userID) throws FundooUserException;

	ResponseDTO verifyUser(String token) throws FundooUserException;

	boolean checkUser(long userId);

	boolean checkIfEmailIsPresent(String emailId);

	Long checkIfUserPresentThenReturnUser(String emailId);

}
