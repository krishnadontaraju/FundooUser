package com.fundoo.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.registration.model.FundooUser;

public interface FundooUserRepository extends JpaRepository<FundooUser , Long>{

	Optional<FundooUser> findByEmailId(String emailid);

}
