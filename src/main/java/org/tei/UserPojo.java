package org.tei;

import lombok.Data;

@Data
public class UserPojo {
    private String name;
    private Integer age;
    private UserAddress userAddress = new UserAddress();
    private Integer lines;

    @Data
    public static class UserAddress {
        private String province;
        private String city;
        private String district;
    }
}
