package com.liao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Score {
    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull(groups = {Add.class, Update.class})
    private Integer studentId;
    @NotEmpty(groups = {Add.class, Update.class})
    private String term;
    @NotEmpty(groups = {Add.class, Update.class})
    private String content;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    public interface Add {}

    public interface Update {}
}
