package com.ctet.repositories;

import org.springframework.stereotype.Repository;

import com.ctet.data.UserTestSession;
@Repository
public interface UserTestSessionService {

	public UserTestSession findUserTestSession(String user, String testName, String companyId);

	public UserTestSession saveOrUpdate(UserTestSession userTestSession);

}
