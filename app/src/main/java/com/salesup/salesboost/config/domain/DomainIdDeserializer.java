package com.salesup.salesboost.config.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.service.UtilService;
import com.salesup.salesboost.service.UtilService.IdCryptor;
import java.io.IOException;

/**
 * This class will work every time when deserialize id of domain objects.
 *
 * <p>HOW TO USE: Decorating domain class '@JsonDeserialize(using = DomainIdDeserializer.class)'
 * with '@JsonSerializationSalt(saltClass = <DeserializationTargetDomain>.class)'
 *
 * <p>Deserialization process is to get the encoded id string, use the decodeId method from the
 * UtilService take the domain object class name as salt and decode the id to Long type
 *
 * <p>Implemented {@link ContextualDeserializer} to get class name as decoder's salt from
 * annotations. Check out an example of {@link Client} about how to use the annotation
 * for @JsonDeserialize and @JsonSerializationSalt annotations
 */
public class DomainIdDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {
  private String deserializationSalt;

  public DomainIdDeserializer() {
    super();
  }

  /**
   * @param deserializationSalt salt needs to be domain class's name. The reason of this is to make
   *     sure the encrypted id for each domain class's id is different. This means for a client
   *     object and a product object both with id 1, their encoded id will be different and the two
   *     different encoded id will always be able to be decoded to 1. Same way goes with
   *     deserialization process.
   */
  public DomainIdDeserializer(String deserializationSalt) {
    this.deserializationSalt = deserializationSalt;
  }

  /**
   * Deserialization Process
   *
   * @param p p as JsonParser will be used to deserialize encoded id String.
   * @param ctxt
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  @Override
  public Long deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String ecodedId = p.readValueAs(String.class);
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    return idCryptor.decodeId(ecodedId, deserializationSalt);
  }

  /**
   * Class name passed from @JsonSerializationSalt will be detected by this class.
   *
   * @param ctxt
   * @param property
   * @return
   * @throws JsonMappingException
   */
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
