package com.salesup.salesboost.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

  // Singleton pattern for getting IdCryptor
  private static IdCryptor idCryptorInstance = null;

  public static IdCryptor getIdCryptorInstance() {
    if (idCryptorInstance == null) {
      idCryptorInstance = new IdCryptor();
    }
    return idCryptorInstance;
  }

  public static class IdCryptor {

    // Notice the difference between IdCryptor and StringIdCryptor: StringIdCryptor cannot be static
    private StringIdCryptor stringIdCryptor;

    public String encodeId(Long id, String domainName) {
      stringIdCryptor = new StringIdCryptor(domainName);
      String idString = base10ToBase62(id.intValue());
      return stringIdCryptor.encodeClientIdString(idString);
    }

    public Long decodeId(String encodedId, String domainName) {
      stringIdCryptor = new StringIdCryptor(domainName);
      String idString = stringIdCryptor.decodeClientId(encodedId);
      int id = base62ToBase10(idString);
      return (long) id;
    }

    private Long decodeId(String encodedId) {
      String idString = stringIdCryptor.decodeClientId(encodedId);
      int id = base62ToBase10(idString);
      return (long) id;
    }

    public List<Long> decodeIdList(List<String> encodedIdList, String domainName) {
      stringIdCryptor = new StringIdCryptor(domainName);
      List<Long> decodedIdList = new ArrayList<>();
      for (String encodedId : encodedIdList) {
        Long decodedId = decodeId(encodedId);
        decodedIdList.add(decodedId);
      }
      return decodedIdList;
    }

    /**
     * Helper function for encoding string to int
     *
     * <p>An example: "00000001" -> 1
     */
    private int base62ToBase10(String s) {
      int n = 0;
      for (int i = 0; i < s.length(); i++) {
        n = n * 62 + convert(s.charAt(i));
      }
      return n;
    }

    /** Helper function for converting from base 62 to base 10. */
    private int convert(char c) {
      if (c >= '0' && c <= '9') return c - '0';
      if (c >= 'a' && c <= 'z') {
        return c - 'a' + 10;
      }
      if (c >= 'A' && c <= 'Z') {
        return c - 'A' + 36;
      }
      return -1;
    }

    /**
     * Helper function for decoding int to string
     *
     * <p>An example: 1 -> "00000001"
     */
    private String base10ToBase62(int n) {
      StringBuilder sb = new StringBuilder();
      while (n != 0) {
        String ELEMENTS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        sb.insert(0, ELEMENTS.charAt(n % 62));
        n /= 62;
      }
      while (sb.length() != 6) {
        sb.insert(0, '0');
      }
      return sb.toString();
    }

    private class StringIdCryptor {
      private TextEncryptor encryptor;

      StringIdCryptor(String domainName) {
        String salt = domainName;
        encryptor = Encryptors.queryableText("SLSBST", Hex.encodeHexString(salt.getBytes()));
      }

      String decodeClientId(String encodedClientId) {
        return encryptor.decrypt(encodedClientId);
      }

      String encodeClientIdString(String clientIdString) {
        return encryptor.encrypt(clientIdString);
      }
    }
  }
}
