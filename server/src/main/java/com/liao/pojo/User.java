package com.liao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import java.time.LocalDateTime;

//lombok，编译阶段自动生成setter，getter，toString
//pom中添加依赖，实体类中添加注解
@Data
public class User {
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Add.class, Update.class, Login.class})
    @Pattern(regexp = "^\\S{2,16}$")
    private String username;
    @JsonIgnore//json 数据里忽略密码
    @NotEmpty(groups = Login.class)
    @Pattern(regexp = "^\\S{4,16}$")
    private String password;
    @Pattern(regexp = "^\\S{4,10}$")//不为空的4-10个字符
    @NotEmpty(groups = {Add.class, Update.class})
    private String type;
    @NotEmpty(groups = {Add.class, Update.class, Login.class})
    private String clazz;
    @NotEmpty(groups = {Add.class, Update.class})
    @Email
    private String email;
    private String master;//班主任名字
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    public interface Add {}

    public interface Update {}

    public interface Login {}
}
