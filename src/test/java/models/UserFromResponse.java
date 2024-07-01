package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFromResponse {
    @JsonProperty("data")
    private UserData userData;
    @JsonProperty("support")
    private UserSupport userSupport;
}
