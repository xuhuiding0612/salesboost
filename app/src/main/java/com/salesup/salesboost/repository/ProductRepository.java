package com.salesup.salesboost.repository;

import com.salesup.salesboost.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
