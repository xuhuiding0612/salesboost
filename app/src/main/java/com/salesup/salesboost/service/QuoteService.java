package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.domain.Quote;
import com.salesup.salesboost.domain.Submitter;
import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.repository.ClientRepository;
import com.salesup.salesboost.repository.ProductRepository;
import com.salesup.salesboost.repository.QuoteRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class QuoteService {
  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
  @Autowired private ClientRepository clientRepository;
  @Autowired private QuoteRepository quoteRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private SubmitterService submitterService;

  /**
   * Service for creating a new quote by following two parameters.
   *
   * @param clientId clientId will be used for querying Client object in database.
   * @param createQuoteDTO contains information about submitter(who submitted quote), timestamp of
   *     submission and product list the submitter interested in (quoted for).
   */
  public void createQuote(Long clientId, CreateQuoteDTO createQuoteDTO) {
    Quote quote = new Quote();
    quote.setQuoteTime(createQuoteDTO.getQuoteTime());

    Client client = clientRepository.getOne(clientId);
    quote.setClient(client);

    List<Long> productIdList = createQuoteDTO.getProductIdList();
    List<Product> productList = productRepository.findAllById(productIdList);
    quote.setProductList(productList);

    // TODO: distinguish submitters to see if they are the same ones. (Like by same name and email
    // address)
    Submitter submitter =
        submitterService.createSubmitter(
            createQuoteDTO.getSubmitterName(),
            createQuoteDTO.getSubmitterEmailAddress(),
            createQuoteDTO.getSubmitterPhoneNumber(),
            createQuoteDTO.getSubmitterCompanyName(),
            createQuoteDTO.getSubmitterTitle(),
            createQuoteDTO.getSubmitterIPAddress());
    quote.setSubmitter(submitter);

    quoteRepository.save(quote);
    logger.info("Saved quote successfully!");
  }
}
