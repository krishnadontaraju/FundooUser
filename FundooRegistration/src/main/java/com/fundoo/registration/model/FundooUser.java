package com.fundoo.registration.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fundoo.registration.dto.FundooUserDTO;

import lombok.Data;

@Entity
@Table(name = "fundoo_user")
@Data
public class FundooUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "first_name")
	@NotNull
	private String fname;
	@Column(name = "last_name")
	private String lname;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "password")
	private String password;
	@Column(name = "phone_no")
	private String phoneno;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	@Column(name = "added_date")
	private LocalDateTime addedDate = LocalDateTime.now();
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	@Column(name = "is_verified")
	private boolean isVerified;
	@Column(name="profile_picture")
	private String profilePicture;
	
	public void updateUser(FundooUserDTO fundooUserDTO) {
		
		this.fname = fundooUserDTO.fname;
		this.lname = fundooUserDTO.lname;
		this.emailId = fundooUserDTO.emailId;
		this.dateOfBirth = fundooUserDTO.dateOfBirth;
		this.profilePicture = fundooUserDTO.profilePicture;
		
	}
	
	public FundooUser(FundooUserDTO fundooUserDTO) {
		this.updateUser(fundooUserDTO);
	}
	
	public FundooUser() {}
}
