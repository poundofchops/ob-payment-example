package org.jt.model.payments;

import java.time.OffsetDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The multiple authorisation flow response from the ASPSP.
 */
@ApiModel(description = "The multiple authorisation flow response from the ASPSP.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-11T15:43:52.706+01:00")

public class OBWriteDomesticResponse3DataMultiAuthorisation   {
  /**
   * Specifies the status of the authorisation flow in code form.
   */
  public enum StatusEnum {
    AUTHORISED("Authorised"),
    
    AWAITINGFURTHERAUTHORISATION("AwaitingFurtherAuthorisation"),
    
    REJECTED("Rejected");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("Status")
  private StatusEnum status = null;

  @JsonProperty("NumberRequired")
  private Integer numberRequired = null;

  @JsonProperty("NumberReceived")
  private Integer numberReceived = null;

  @JsonProperty("LastUpdateDateTime")
  private OffsetDateTime lastUpdateDateTime = null;

  @JsonProperty("ExpirationDateTime")
  private OffsetDateTime expirationDateTime = null;

  public OBWriteDomesticResponse3DataMultiAuthorisation status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Specifies the status of the authorisation flow in code form.
   * @return status
  **/
  @ApiModelProperty(required = true, value = "Specifies the status of the authorisation flow in code form.")
  @NotNull


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public OBWriteDomesticResponse3DataMultiAuthorisation numberRequired(Integer numberRequired) {
    this.numberRequired = numberRequired;
    return this;
  }

  /**
   * Number of authorisations required for payment order (total required at the start of the multi authorisation journey).
   * @return numberRequired
  **/
  @ApiModelProperty(value = "Number of authorisations required for payment order (total required at the start of the multi authorisation journey).")


  public Integer getNumberRequired() {
    return numberRequired;
  }

  public void setNumberRequired(Integer numberRequired) {
    this.numberRequired = numberRequired;
  }

  public OBWriteDomesticResponse3DataMultiAuthorisation numberReceived(Integer numberReceived) {
    this.numberReceived = numberReceived;
    return this;
  }

  /**
   * Number of authorisations received.
   * @return numberReceived
  **/
  @ApiModelProperty(value = "Number of authorisations received.")


  public Integer getNumberReceived() {
    return numberReceived;
  }

  public void setNumberReceived(Integer numberReceived) {
    this.numberReceived = numberReceived;
  }

  public OBWriteDomesticResponse3DataMultiAuthorisation lastUpdateDateTime(OffsetDateTime lastUpdateDateTime) {
    this.lastUpdateDateTime = lastUpdateDateTime;
    return this;
  }

  /**
   * Last date and time at the authorisation flow was updated.All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00
   * @return lastUpdateDateTime
  **/
  @ApiModelProperty(value = "Last date and time at the authorisation flow was updated.All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00")

  @Valid

  public OffsetDateTime getLastUpdateDateTime() {
    return lastUpdateDateTime;
  }

  public void setLastUpdateDateTime(OffsetDateTime lastUpdateDateTime) {
    this.lastUpdateDateTime = lastUpdateDateTime;
  }

  public OBWriteDomesticResponse3DataMultiAuthorisation expirationDateTime(OffsetDateTime expirationDateTime) {
    this.expirationDateTime = expirationDateTime;
    return this;
  }

  /**
   * Date and time at which the requested authorisation flow must be completed.All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00
   * @return expirationDateTime
  **/
  @ApiModelProperty(value = "Date and time at which the requested authorisation flow must be completed.All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00")

  @Valid

  public OffsetDateTime getExpirationDateTime() {
    return expirationDateTime;
  }

  public void setExpirationDateTime(OffsetDateTime expirationDateTime) {
    this.expirationDateTime = expirationDateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OBWriteDomesticResponse3DataMultiAuthorisation obWriteDomesticResponse3DataMultiAuthorisation = (OBWriteDomesticResponse3DataMultiAuthorisation) o;
    return Objects.equals(this.status, obWriteDomesticResponse3DataMultiAuthorisation.status) &&
        Objects.equals(this.numberRequired, obWriteDomesticResponse3DataMultiAuthorisation.numberRequired) &&
        Objects.equals(this.numberReceived, obWriteDomesticResponse3DataMultiAuthorisation.numberReceived) &&
        Objects.equals(this.lastUpdateDateTime, obWriteDomesticResponse3DataMultiAuthorisation.lastUpdateDateTime) &&
        Objects.equals(this.expirationDateTime, obWriteDomesticResponse3DataMultiAuthorisation.expirationDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, numberRequired, numberReceived, lastUpdateDateTime, expirationDateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OBWriteDomesticResponse3DataMultiAuthorisation {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    numberRequired: ").append(toIndentedString(numberRequired)).append("\n");
    sb.append("    numberReceived: ").append(toIndentedString(numberReceived)).append("\n");
    sb.append("    lastUpdateDateTime: ").append(toIndentedString(lastUpdateDateTime)).append("\n");
    sb.append("    expirationDateTime: ").append(toIndentedString(expirationDateTime)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
