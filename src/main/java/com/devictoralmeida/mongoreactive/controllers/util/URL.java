package com.devictoralmeida.mongoreactive.controllers.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class URL {
  private URL() {
  }

  public static String decodeParam(String text) {
    return URLDecoder.decode(text, StandardCharsets.UTF_8);
  }

  public static Instant convertDate(String textDate, Instant defaultValue) {
    if (textDate.isEmpty()) {
      return defaultValue;
    }

    return Instant.parse(textDate);
  }
}
