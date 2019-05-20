package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * The authorisation type request from the TPP.
 */
@ApiModel(description = "The authorisation type request from the TPP.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-17T22:15:14.722+01:00")

public class OBAuthorisation1   {
  @JsonProperty("AuthorisationType")
  private OBExternalAuthorisation1Code authorisationType = null;

  @JsonProperty("CompletionDateTime")
  private OffsetDateTime completionDateTime = null;

  public OBAuthorisation1 authorisationType(OBExternalAuthorisation1Code authorisationType) {
    this.authorisationType = authorisationType;
    return this;
  }

  /**
   * Get authorisationType
   * @return authorisationType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OBExternalAuthorisation1Code getAuthorisationType() {
    return authorisationType;
  }

  public void setAuthorisationType(OBExternalAuthorisation1Code authorisationType) {
    this.authorisationType = authorisationType;
  }

  public OBAuthorisation1 completionDateTime(OffsetDateTime completionDateTime) {
    this.completionDateTime = completionDateTime;
    return this;
  }

  /**
   * Date and time at which the requested authorisation flow must be completed. All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00
   * @return completionDateTime
  **/
  @ApiModelProperty(value = "Date and time at which the requested authorisation flow must be completed. All dates in the JSON payloads are represented in ISO 8601 date-time format.  All date-time fields in responses must include the timezone. An example is below: 2017-04-05T10:43:07+00:00")

  @Valid

  public OffsetDateTime getCompletionDateTime() {
    return completionDateTime;
  }

  public void setCompletionDateTime(OffsetDateTime completionDateTime) {
    this.completionDateTime = completionDateTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OBAuthorisation1 obAuthorisation1 = (OBAuthorisation1) o;
    return Objects.equals(this.authorisationType, obAuthorisation1.authorisationType) &&
        Objects.equals(this.completionDateTime, obAuthorisation1.completionDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorisationType, completionDateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OBAuthorisation1 {\n");

    sb.append("    authorisationType: ").append(toIndentedString(authorisationType)).append("\n");
    sb.append("    completionDateTime: ").append(toIndentedString(completionDateTime)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

