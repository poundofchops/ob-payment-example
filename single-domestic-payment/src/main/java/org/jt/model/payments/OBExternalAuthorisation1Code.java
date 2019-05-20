package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type of authorisation flow requested.
 */
public enum OBExternalAuthorisation1Code {
  
  ANY("Any"),
  
  SINGLE("Single");

  private String value;

  OBExternalAuthorisation1Code(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OBExternalAuthorisation1Code fromValue(String text) {
    for (OBExternalAuthorisation1Code b : OBExternalAuthorisation1Code.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

