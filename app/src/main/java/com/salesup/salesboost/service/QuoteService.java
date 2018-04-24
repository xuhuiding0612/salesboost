package com.salesup.salesboost.service;

import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.domain.Quote;
import com.salesup.salesboost.domain.Submitter;
import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.repository.ClientRepository;
import com.salesup.salesboost.repository.ProductRepository;
import com.salesup.salesboost.repository.QuoteRepository;
import com.salesup.salesboost.service.UtilService.IdCryptor;
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
  @Autowired private UtilService utilService;

  /**
   * Service for getting existing quotes by clientId.
   *
   * @param encodedClientId encodedClientId will be decoded first and then be used when getting all
   *     the quotes with that clientId as reference.
   * @return A list of quotes with the input clientId.
   */
  public List<Quote> getQuotes(String encodedClientId) {
    // decoding "encodedClientId" to get clientId
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    Long clientId = idCryptor.decodeId(encodedClientId, Client.class.getSimpleName());
    return quoteRepository.findAllByClientId(clientId);
  }

  /**
   * Service for creating a new quote by following two parameters.
   *
   * @param encodedClientId encodedClientId will be decoded first and then be used for querying
   *     Client object in database.
   * @param createQuoteDTO contains information about submitter(who submitted quote), timestamp of
   *     submission and product list (in form of encoded product id list) the submitter interested
   *     in (quoted for).
   */
  public void createQuote(String encodedClientId, CreateQuoteDTO createQuoteDTO) {
    Quote quote = new Quote();
    quote.setQuoteTime(createQuoteDTO.getQuoteTime());

    // decoding "encodedClientId" to get clientId
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    Long clientId = idCryptor.decodeId(encodedClientId, Client.class.getSimpleName());
    Client client = clientRepository.getOne(clientId);
    quote.setClient(client);

    // decoding "encodedProductId" in encodedProductIdList to get list of productId
    List<String> encodedProductIdList = createQuoteDTO.getProductIdList();
    List<Long> productIdList =
        idCryptor.decodeIdList(encodedProductIdList, Product.class.getSimpleName());
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
    logger.info("Created a quote successfully!");
  }
}
