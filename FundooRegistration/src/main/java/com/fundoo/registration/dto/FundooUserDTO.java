package com.fundoo.registration.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FundooUserDTO {

	public String fname;
	public String lname;
	public String emailId;
	public String password;
	public String phoneno;
	@JsonFormat(pattern = "dd MMM yyyy")
	public LocalDate dateOfBirth;
	public String profilePicture;
	
}
