package com.fundoo.registration.service;

import java.util.Optional;

import com.fundoo.registration.dto.FundooUserDTO;
import com.fundoo.registration.dto.ResponseDTO;
import com.fundoo.registration.exception.FundooUserException;
import com.fundoo.registration.model.FundooUser;

public interface IFundooUserService {

	ResponseDTO viewAllUsers();

	ResponseDTO registerUser(FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO updateEmployeePayRollData(long userID, FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO deleteEmployeePayRollData(long userID) throws FundooUserException;

	ResponseDTO verifyUser(String token) throws FundooUserException;

	boolean checkUser(long userId);

	boolean checkIfEmailIsPresent(String emailId);

	Long checkIfUserPresentThenReturnUser(String emailId);

}
