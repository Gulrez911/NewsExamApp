package com.ctet.services;

import com.ctet.data.UserTestTimeCounter;

public interface UserTestTimeCounterService {

	public UserTestTimeCounter saveOrUpdate(UserTestTimeCounter userTestTimeCounter);

	public UserTestTimeCounter findByPrimaryKey(Long testId, String email, String companyId);

}