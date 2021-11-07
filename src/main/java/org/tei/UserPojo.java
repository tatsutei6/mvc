package org.tei;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserPojo {
    private String name;
    private Integer age;
    private UserAddress userAddress = new UserAddress();
    @JsonIgnore
    private Integer lines;

    @Data
    public static class UserAddress {
        private String province;
        private String city;
        private String district;
    }
}
