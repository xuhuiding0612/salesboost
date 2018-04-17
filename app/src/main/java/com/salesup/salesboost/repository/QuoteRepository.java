package com.salesup.salesboost.repository;

import com.salesup.salesboost.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {}
