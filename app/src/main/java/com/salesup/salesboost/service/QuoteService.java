package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.Customer;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.domain.Quote;
import com.salesup.salesboost.domain.Submitter;
import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.repository.CustomerRepository;
import com.salesup.salesboost.repository.ProductRepository;
import com.salesup.salesboost.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SubmitterService submitterService;

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    public void createQuote(Long customerId, CreateQuoteDTO createQuoteDTO) {
        Quote quote = new Quote();
        Customer customer = customerRepository.getOne(customerId);
        quote.setCustomer(customer);

        List<Long> productIdList = createQuoteDTO.getProductIdList();
        List<Product> productList = productRepository.findAllById(productIdList);
        quote.setProductList(productList);

        Submitter submitter = submitterService.createSubmitter(createQuoteDTO.getSubmitterName(),
                createQuoteDTO.getSubmitterEmailAddress(), createQuoteDTO.getSubmitterPhoneNumber(),
                createQuoteDTO.getSubmitterCompanyName(), createQuoteDTO.getSubmitterTitle(),
                createQuoteDTO.getSubmitterIPAddress());
        quote.setSubmitter(submitter);

        quoteRepository.save(quote);
        logger.info("Saved quote successfully!");
    }
}
