package com.salesup.salesboost.controller;

import com.salesup.salesboost.dto.CreateQuoteDTO;
import com.salesup.salesboost.dto.ResponseDTO;
import com.salesup.salesboost.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @RequestMapping(value = "/quote", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO<Void>> createQuote(@RequestParam Long customerId,
                                                @RequestBody CreateQuoteDTO createQuoteDTO) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        // TODO: verify customer authentication
        quoteService.createQuote(customerId, createQuoteDTO);
        responseDTO.addMessage("Added a quote successfully!");
        responseDTO.setSuccess(true);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // TODO: custom error handling
//    @ControllerAdvice
//    public class ExceptionHandlerAdvice {
//
//        @ExceptionHandler(RuntimeException.class)
//        public ResponseEntity handleException(RuntimeException e) {
//            // log exception
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .body("Error Message");
//        }
//    }

}