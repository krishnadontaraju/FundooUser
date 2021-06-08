package com.fundoo.registration.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FundooUserDTO {

	public String fname;
	public String lname;
	public String emailId;
	public String password;
	public String phoneno;
	public LocalDate dateOfBirth;
	public String profilePicture;
	
}
