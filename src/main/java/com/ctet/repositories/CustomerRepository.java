package com.ctet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctet.data.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
