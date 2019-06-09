package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the status of the authorisation flow in code form.
 */
public enum OBExternalStatus2Code {
  
  AUTHORISED("Authorised"),
  
  AWAITINGFURTHERAUTHORISATION("AwaitingFurtherAuthorisation"),
  
  REJECTED("Rejected");

  private String value;

  OBExternalStatus2Code(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OBExternalStatus2Code fromValue(String text) {
    for (OBExternalStatus2Code b : OBExternalStatus2Code.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

