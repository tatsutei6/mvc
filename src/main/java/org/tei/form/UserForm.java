package org.tei.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @NotEmpty(message="用户名不能为空")
    @Length(min = 2, max = 32, message = "用户名长度在2到32个字符之间")
    private String username;

    @NotEmpty(message="密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度在6到32个字符之间")
    private String password;
}
