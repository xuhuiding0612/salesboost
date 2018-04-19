package com.salesup.salesboost.controller;

import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.dto.ResponseDTO;
import com.salesup.salesboost.service.QuoteService;
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

  /**
   * Create a quote and store in database. Clients need to provide id and dto in their requests.
   * Request URL: /quote Request Method: POST
   *
   * @param clientId Request Parameter clientId is expected to be passed from client's frontend.
   *     Assuming client's frontend knows the id of Client object in backend database.(The id can be
   *     passed to frontend by other backend endpoints.)
   * @param createQuoteDTO Request Body CreateQuoteDTO contains information about submitter(who
   *     submitted quote), timestamp of submission and product list the submitter interested in
   *     (quoted for). CreateQuoteDTO contains information of submitter including: ip address
   *     (IPv4), name, email address, phone number, title, company name
   * @return Empty result with success/fail messages.
   */
  @RequestMapping(value = "/quote", method = RequestMethod.POST)
  public ResponseEntity<ResponseDTO<Void>> createQuote(
      @RequestParam Long clientId, @RequestBody CreateQuoteDTO createQuoteDTO) {
    ResponseDTO<Void> responseDTO = new ResponseDTO<>();

    // TODO: verify client authentication
    quoteService.createQuote(clientId, createQuoteDTO);
    responseDTO.addMessage("Added a quote successfully!");
    responseDTO.setSuccess(true);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }
}
