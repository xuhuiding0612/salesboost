package com.salesup.salesboost.loader;

import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.repository.ClientRepository;
import com.salesup.salesboost.repository.ProductRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyDataLoader implements ApplicationRunner {
  @Autowired private ClientRepository clientRepository;
  @Autowired private ProductRepository productRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Client client = new Client();
    client.setName("Xiang Xiang Corgi Dog Supply Corps.");
    clientRepository.save(client);

    Product product1 = new Product();
    product1.setName("cute corgi's butt");

    Product product2 = new Product();
    product2.setName("short corgi's leg");
    productRepository.saveAll(Arrays.asList(product1, product2));
  }
}
