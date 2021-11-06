package org.tei;

import lombok.Data;

@Data
public class UserUpdateForm {
    private String name;
    private Integer age;
    private String province;
    private String city;
    private String district;
}
