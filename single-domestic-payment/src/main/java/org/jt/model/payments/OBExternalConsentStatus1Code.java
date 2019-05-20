package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the status of resource in code form.
 */
public enum OBExternalConsentStatus1Code {
  
  AUTHORISED("Authorised"),
  
  AWAITINGAUTHORISATION("AwaitingAuthorisation"),
  
  CONSUMED("Consumed"),
  
  REJECTED("Rejected");

  private String value;

  OBExternalConsentStatus1Code(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OBExternalConsentStatus1Code fromValue(String text) {
    for (OBExternalConsentStatus1Code b : OBExternalConsentStatus1Code.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

