package com.salesup.salesboost.repository;

import com.salesup.salesboost.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
