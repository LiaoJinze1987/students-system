package com.liao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

//班级
@Data
public class Clazz {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Add.class, Update.class})
    private String name;
    @NotEmpty(groups = {Add.class, Update.class})
    private String header;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    public interface Add {}

    public interface Update {}
}
