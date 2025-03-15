package com.ctet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctet.data.Company;
import com.ctet.repositories.CompanyRepository;
import com.ctet.services.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired	
	CompanyRepository companyRepository;
	
//	@Override
//	public void saveOrUpdate(Company company) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public Company findByPrimaryKey(String companyName, String companyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Company findByCompanyName(String companyName) {
		// TODO Auto-generated method stub
		return companyRepository.findByCompanyNameIgnoreCase(companyName);
	}

	@Override
	public Company findByCompanyId(String companyId) {
		Company comp = companyRepository.findByCompanyId(companyId);
		return comp;
	}

}
