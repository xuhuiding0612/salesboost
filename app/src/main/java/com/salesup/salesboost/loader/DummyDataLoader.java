package com.salesup.salesboost.loader;

import com.salesup.salesboost.domain.Customer;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.repository.CustomerRepository;
import com.salesup.salesboost.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DummyDataLoader implements ApplicationRunner {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Customer customer = new Customer();
        customer.setName("Xiang Xiang Corgi Dog Supply Corps.");
        customerRepository.save(customer);

        Product product1 = new Product();
        product1.setName("cute corgi's butt");

        Product product2 = new Product();
        product2.setName("short corgi's leg");
        productRepository.saveAll(Arrays.asList(product1, product2));
    }
}
