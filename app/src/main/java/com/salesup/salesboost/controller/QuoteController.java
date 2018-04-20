package com.salesup.salesboost.controller;

import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.domain.Product;
import com.salesup.salesboost.domain.Quote;
import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.dto.ResponseDTO;
import com.salesup.salesboost.service.QuoteService;
import com.salesup.salesboost.service.UtilService;
import com.salesup.salesboost.service.UtilService.IdCryptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** */
@RestController
public class QuoteController {

  @Autowired private QuoteService quoteService;
  @Autowired private UtilService utilService;

  /**
   * Get existing quotes in database. Need clients to provide the encoded client id (backend will
   * decode it to get actual clientId for querying)
   *
   * <p>Request URL: /quote
   *
   * <p>Request Method: GET
   *
   * @param encodedClientId Only required Request Parameter. Decode the encoded clientId first to
   *     get clientId and get all the existing quotes by clientId
   * @return Success message will contain message, ifSuccess and result with list of quotes.
   */
  @RequestMapping(value = "/quote", method = RequestMethod.GET)
  public ResponseEntity<ResponseDTO<List<Quote>>> getQuotes(@RequestParam String encodedClientId) {
    ResponseDTO<List<Quote>> responseDTO = new ResponseDTO<>();

    // TODO: verify client authentication
    List<Quote> quoteList = quoteService.getQuotes(encodedClientId);
    responseDTO.setResult(quoteList);
    responseDTO.addMessage("Get quotes successfully!");
    responseDTO.setSuccess(true);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  /**
   * Create a quote and store in database. Needs encodedClientId (add quote to which client) and dto
   * in their requests.
   *
   * <p>Request URL: /quote
   *
   * <p>Request Method: POST
   *
   * @param encodedClientId Request Parameter encodedClientId is expected to be passed from client's
   *     frontend. Assuming client's frontend knows the encoded id of Client object in backend
   *     database.(The id can be passed to frontend by other backend endpoints.)
   * @param createQuoteDTO Request Body CreateQuoteDTO contains information about submitter(who
   *     submitted quote), timestamp of submission and product list (in form of encoded product id
   *     list) the submitter interested in (quoted for). CreateQuoteDTO contains information of
   *     submitter including: ip address (IPv4), name, email address, phone number, title, company
   *     name
   * @return Empty result with success/fail messages.
   */
  @RequestMapping(value = "/quote", method = RequestMethod.POST)
  public ResponseEntity<ResponseDTO<Void>> createQuote(
      @RequestParam String encodedClientId, @RequestBody CreateQuoteDTO createQuoteDTO) {
    ResponseDTO<Void> responseDTO = new ResponseDTO<>();

    // TODO: verify client authentication
    quoteService.createQuote(encodedClientId, createQuoteDTO);
    responseDTO.addMessage("Create a quote successfully!");
    responseDTO.setSuccess(true);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  /**
   * Temporary backdoor method for getting encoded client ids TODO: Remove this block and pass the
   * encoded ids to clients' frontend
   */
  @RequestMapping(value = "/encode/client", method = RequestMethod.GET)
  public ResponseEntity<ResponseDTO<String>> encodeClientId(@RequestParam Long clientId) {
    ResponseDTO<String> responseDTO = new ResponseDTO<>();
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    String encodedClientId = idCryptor.encodeId(clientId, Client.class.getSimpleName());
    responseDTO.addMessage(
        String.format("Encoded %s Id %s successfully!", Client.class.getSimpleName(), clientId));
    responseDTO.setSuccess(true);
    responseDTO.setResult(encodedClientId);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  /**
   * Temporary backdoor method for getting encoded product ids TODO: Remove this block and pass the
   * encoded ids to clients' frontend
   */
  @RequestMapping(value = "/encode/product", method = RequestMethod.GET)
  public ResponseEntity<ResponseDTO<String>> encodeProductId(@RequestParam Long productId) {
    ResponseDTO<String> responseDTO = new ResponseDTO<>();
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    String encodedProductId = idCryptor.encodeId(productId, Product.class.getSimpleName());
    responseDTO.addMessage(
        String.format("Encoded %s Id %s successfully!", Product.class.getSimpleName(), productId));
    responseDTO.setSuccess(true);
    responseDTO.setResult(encodedProductId);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }
}
