package com.ctet.services;

import java.util.List;

import com.ctet.data.CandidateProfileParams;

public interface CandidateProfileParamsService {

	public CandidateProfileParams findUniqueCandidateProfileParams(String qualifier1, String qualifier2, String qualifier3, String qualifier4,
			String qualifier5);

	public void saveOrUpdate(CandidateProfileParams candidateProfileParams);

//	public List<CandidateProfileParams> findCandidateProfileParamsByCompanyId(String companyId);
}