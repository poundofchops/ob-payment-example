package io.swagger.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

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

