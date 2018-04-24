package com.salesup.salesboost.config.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.salesup.salesboost.domain.Client;
import com.salesup.salesboost.service.UtilService;
import com.salesup.salesboost.service.UtilService.IdCryptor;
import java.io.IOException;

/**
 * This class will work every time when serialize id of domain objects.
 *
 * <p>HOW TO USE: Decorating domain class '@JsonSerialize(using = DomainIdSerializer.class)' with
 * '@JsonSerializationSalt(saltClass = <DeserializationTargetDomain>.class)'
 *
 * <p>Serialization process is to get the id in Long type, use the encodedId method from the
 * UtilService take the domain object class name as salt and encode the id in Long type to String.
 *
 * <p>Implemented {@link ContextualDeserializer} to get class name as encoder's salt from
 * annotations. Check out an example of {@link Client} about how to use the annotation
 * for @JsonSerialize and @JsonSerializationSalt annotations
 */
public class DomainIdSerializer extends JsonSerializer<Long> implements ContextualSerializer {
  private String serializationSalt;

  public DomainIdSerializer() {
    super();
  }

  /**
   * @param serializationSalt salt needs to be domain class's name. The reason of this is to make
   *     sure the encrypted id for each domain class's id is different. This means for a client
   *     object and a product object both with id 1, their encoded id will be different and the two
   *     different encoded id will always be able to be decoded to 1. Same way goes with
   *     deserialization process.
   */
  public DomainIdSerializer(String serializationSalt) {
    this.serializationSalt = serializationSalt;
  }

  /**
   * Serialization Process
   *
   * @param id real id in database in {@link Long} type
   * @param gen
   * @param provider
   * @throws IOException
   */
  @Override
  public void serialize(Long id, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    String encodedId = idCryptor.encodeId(id, serializationSalt);
    gen.writeObject(encodedId);
  }

  /**
   * Class name passed from @JsonSerializationSalt will be detected by this class.
   *
   * @param prov
   * @param property
   * @return
   * @throws JsonMappingException
   */
  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {
    String serializationSalt = null;
    JsonSerializationSalt annotationInput = null;
    if (property != null) {
      annotationInput = property.getAnnotation(JsonSerializationSalt.class);
    }
    if (annotationInput != null) {
      serializationSalt = annotationInput.saltClass().getSimpleName();
    }
    return new DomainIdSerializer(serializationSalt);
  }
}
