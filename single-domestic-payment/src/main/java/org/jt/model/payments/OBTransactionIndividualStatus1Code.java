package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the status of the payment information group.
 */
public enum OBTransactionIndividualStatus1Code {
  
  ACCEPTEDSETTLEMENTCOMPLETED("AcceptedSettlementCompleted"),
  
  ACCEPTEDSETTLEMENTINPROCESS("AcceptedSettlementInProcess"),
  
  PENDING("Pending"),
  
  REJECTED("Rejected");

  private String value;

  OBTransactionIndividualStatus1Code(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OBTransactionIndividualStatus1Code fromValue(String text) {
    for (OBTransactionIndividualStatus1Code b : OBTransactionIndividualStatus1Code.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

