package com.example.addressapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.addressapi.model.Address;
import com.example.addressapi.repository.AddressRepository;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

   

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
    	
    	
    	System.out.println("Address to update :" +address.toString());
    	List<Address> listOfAddress = addressRepository.getAddressByEmployeeId(address.getEmpId());
    	if(listOfAddress.isEmpty()) {
    		System.out.println("No record");
    		addressRepository.save(address); 
    		}
    	
    	else {
    		System.out.println("Record found");
    		Address addrinDB = listOfAddress.get(0);
    		if(address.getAddressLine1()!=null) addrinDB.setAddressLine1(address.getAddressLine1());
    		addrinDB.setAddressLine2(address.getAddressLine2());
    		addrinDB.setCity(address.getCity());
    		addrinDB.setState(address.getState());
    		addrinDB.setCountry(address.getCountry());
    		
    		//TODO same for otherfields except empid
    		
    		addressRepository.save(addrinDB); 
    	}
    	
		 
		
        return address;
    }
    
    @GetMapping(value = "/{empid}")
	public Address getEmployeeAddress(@PathVariable Long empid) {
    	
    	List<Address> listOfAddress = addressRepository.getAddressByEmployeeId(empid);
    	if(listOfAddress.isEmpty()) {
    		System.out.println("No record");
    		return null;
    		}
    	
    	else {
    		System.out.println("Record found");
    		return listOfAddress.get(0);
    	}
 
	}

	@PutMapping("/{id}") 
	public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) { 
		if(!addressRepository.existsById(id)) { 
			return ResponseEntity.notFound().build(); 
			} 
		address.setId(id); 
		Address updatedAddress = addressRepository.save(address); 
		return ResponseEntity.ok(updatedAddress); 
		}
	
		
    @DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteAddress(@PathVariable Long id) {
		if (addressRepository.existsById(id)) {
			addressRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.noContent().build();
		}
 
	}
}
