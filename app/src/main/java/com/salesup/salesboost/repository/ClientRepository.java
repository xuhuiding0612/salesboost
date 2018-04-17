package com.salesup.salesboost.repository;

import com.salesup.salesboost.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
