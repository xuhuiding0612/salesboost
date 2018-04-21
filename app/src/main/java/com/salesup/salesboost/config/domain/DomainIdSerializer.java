package com.salesup.salesboost.config.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.salesup.salesboost.service.UtilService;
import com.salesup.salesboost.service.UtilService.IdCryptor;
import java.io.IOException;

public class DomainIdSerializer extends JsonSerializer<Long> implements ContextualSerializer {
  private String serializationSalt;

  public DomainIdSerializer() {
    super();
  }

  public DomainIdSerializer(String serializationSalt) {
    this.serializationSalt = serializationSalt;
  }

  @Override
  public void serialize(Long id, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    IdCryptor idCryptor = UtilService.getIdCryptorInstance();
    String encodedId = idCryptor.encodeId(id, serializationSalt);
    gen.writeObject(encodedId);
  }

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
