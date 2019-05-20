package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the payment context
 */
public enum OBExternalPaymentContext1Code {
  
  BILLPAYMENT("BillPayment"),
  
  ECOMMERCEGOODS("EcommerceGoods"),
  
  ECOMMERCESERVICES("EcommerceServices"),
  
  OTHER("Other"),
  
  PARTYTOPARTY("PartyToParty");

  private String value;

  OBExternalPaymentContext1Code(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OBExternalPaymentContext1Code fromValue(String text) {
    for (OBExternalPaymentContext1Code b : OBExternalPaymentContext1Code.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

