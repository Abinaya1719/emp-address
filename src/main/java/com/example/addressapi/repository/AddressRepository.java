package com.example.addressapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.addressapi.model.Address;



public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Query("SELECT a FROM Address a WHERE a.empId = :empId")
	List<Address> getAddressByEmployeeId(@Param("empId") Long empId); 

}
