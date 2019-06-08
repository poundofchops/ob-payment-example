package io.swagger.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

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

