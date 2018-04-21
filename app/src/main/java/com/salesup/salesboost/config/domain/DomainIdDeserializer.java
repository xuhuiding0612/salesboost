package com.salesup.salesboost.config.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.salesup.salesboost.service.UtilService;
import com.salesup.salesboost.service.UtilService.IdCryptor;
import java.io.IOException;

public class DomainIdDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {
  private String deserializationSalt;

  public DomainIdDeserializer() {
    super();
  }

  public DomainIdDeserializer(String deserializationSalt) {
    this.deserializationSalt = deserializationSalt;
  }

  @Override
  public Long deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String ecodedId = p.readValueAs(String.class);
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    return idCryptor.decodeId(ecodedId, deserializationSalt);
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
      throws JsonMappingException {
    String deserializationSalt = null;
    JsonSerializationSalt annotationInput = null;
    if (property != null) {
      annotationInput = property.getAnnotation(JsonSerializationSalt.class);
    }
    if (annotationInput != null) {
      deserializationSalt = annotationInput.saltClass().getSimpleName();
    }
    return new DomainIdDeserializer(deserializationSalt);
  }
}
