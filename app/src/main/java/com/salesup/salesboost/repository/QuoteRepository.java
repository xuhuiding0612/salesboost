package com.salesup.salesboost.repository;

import com.salesup.salesboost.domain.Quote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
  List<Quote> findAllByClientId(Long clientId);
}
