package com.salesup.salesboost.loader;

import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.repository.CustomerRepository;
import com.salesup.salesboost.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class DummyDataLoader implements ApplicationRunner {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
