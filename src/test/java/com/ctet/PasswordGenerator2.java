package com.ctet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ctet.data.Company;
import com.ctet.services.CompanyService;

@SpringBootTest
public class PasswordGenerator2 {

	@Autowired
	CompanyService companyService;

	@DisplayName("Test Spring @Autowired Integration")
	@Test
	void testGet() {
		Company company = companyService.findByCompanyName("MC");
		System.out.println("ss " + company);
		assertEquals("MC", companyService.findByCompanyId("MC"));
	}

//	public static void main(String[] args) {
//		Company company = companyService.
//	}

}
