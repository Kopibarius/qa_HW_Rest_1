package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFromRequest {
    @JsonProperty("data")
    private UserData userData;
    @JsonProperty("support")
    private UserSupport userSupport;
}
