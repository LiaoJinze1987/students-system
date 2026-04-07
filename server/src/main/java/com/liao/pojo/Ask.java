package com.liao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ask {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Add.class, Update.class})
    private String type;
    @NotEmpty(groups = {Add.class, Update.class})
    private String content;
    @NotNull(groups = {Add.class})
    private Integer studentId;
    @NotEmpty(groups = {Add.class})
    private String studentName;
    @NotNull(groups = {Add.class, Update.class})
    private Integer masterId;
    @NotEmpty(groups = {Add.class, Update.class})
    private String studentMaster;
    @NotNull(groups = {Update.class})
    private Integer status;//默认0：未处理，1，同意，2，拒绝
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    public interface Add {}

    public interface Update {}
}
