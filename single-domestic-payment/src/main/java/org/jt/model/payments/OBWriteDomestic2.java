package org.jt.model.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * OBWriteDomestic2
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-17T22:15:14.722+01:00")

public class OBWriteDomestic2   {
  @JsonProperty("Data")
  private OBWriteDataDomestic2 data = null;

  @JsonProperty("Risk")
  private OBRisk1 risk = null;

  public OBWriteDomestic2 data(OBWriteDataDomestic2 data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OBWriteDataDomestic2 getData() {
    return data;
  }

  public void setData(OBWriteDataDomestic2 data) {
    this.data = data;
  }

  public OBWriteDomestic2 risk(OBRisk1 risk) {
    this.risk = risk;
    return this;
  }

  /**
   * Get risk
   * @return risk
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OBRisk1 getRisk() {
    return risk;
  }

  public void setRisk(OBRisk1 risk) {
    this.risk = risk;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OBWriteDomestic2 obWriteDomestic2 = (OBWriteDomestic2) o;
    return Objects.equals(this.data, obWriteDomestic2.data) &&
        Objects.equals(this.risk, obWriteDomestic2.risk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, risk);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OBWriteDomestic2 {\n");

    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    risk: ").append(toIndentedString(risk)).append("\n");
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

