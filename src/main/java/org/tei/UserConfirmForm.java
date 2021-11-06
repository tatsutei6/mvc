package org.tei;

import lombok.Data;

@Data
public class UserConfirmForm {
    private String id;
    private UserPojo[] userPojoArr;
}
