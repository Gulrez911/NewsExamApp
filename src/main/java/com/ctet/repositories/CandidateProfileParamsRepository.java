package com.ctet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.data.CandidateProfileParams;
@Repository
public interface CandidateProfileParamsRepository extends JpaRepository<CandidateProfileParams, Long> {

	@Query(value = "SELECT q FROM CandidateProfileParams q WHERE q.qualifier1=:qualifier1 and q.qualifier2=:qualifier2 and q.qualifier3=:qualifier3 and q.qualifier4=:qualifier4 and q.qualifier5=:qualifier5")
	public CandidateProfileParams findUniqueCandidateProfileParams(@Param("qualifier1") String qualifier1, @Param("qualifier2") String qualifier2,
			@Param("qualifier3") String qualifier3, @Param("qualifier4") String qualifier4, @Param("qualifier5") String qualifier5);

//	@Query(value = "SELECT q FROM CandidateProfileParams q WHERE q.companyId=:companyId order by q.qualifier1")
//	public List<CandidateProfileParams> findCandidateProfileParamsByCompanyId(@Param("companyId") String companyId);
//
//	@Query(value = "SELECT q FROM CandidateProfileParams q WHERE q.companyId=:companyId AND  (q.qualifier1 LIKE :character%)")
//	public List<CandidateProfileParams> findCandidateProfileParamsByCharacter(@Param("companyId") String companyId, @Param("character") String character);

}