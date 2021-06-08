package com.fundoo.registration.service;

import com.fundoo.registration.dto.FundooUserDTO;
import com.fundoo.registration.dto.ResponseDTO;
import com.fundoo.registration.exception.FundooUserException;

public interface IFundooUserService {

	ResponseDTO viewAllUsers();

	ResponseDTO registerUser(FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO updateEmployeePayRollData(long userID, FundooUserDTO userDTO) throws FundooUserException;

	ResponseDTO deleteEmployeePayRollData(long userID) throws FundooUserException;

	ResponseDTO verifyUser(String token) throws FundooUserException;

}
