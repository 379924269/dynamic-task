package com.huazai.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "ScheduleSetting对象", description = "计划设置信息")
public class ScheduleSetting implements Serializable {
    @TableId(value = "job_id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务ID")
    private Integer jobId;
    @ApiModelProperty(value = "bean名称")
    private String beanName;
    @ApiModelProperty(value = "方法名称")
    private String methodName;
    @ApiModelProperty(value = "方法参数")
    private String methodParams;
    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;
    @ApiModelProperty(value = "状态（1正常 0暂停）")
    private Integer jobStatus;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime gmtModified;

    public ScheduleSetting() {
    }

    public ScheduleSetting(Integer jobId, String beanName, String methodName, String methodParams, String cronExpression, Integer jobStatus, String remark, LocalDateTime gmtCreate, LocalDateTime gmtModified) {
        this.jobId = jobId;
        this.beanName = beanName;
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.cronExpression = cronExpression;
        this.jobStatus = jobStatus;
        this.remark = remark;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public ScheduleSetting(String beanName, String methodName, String methodParams, String cronExpression, Integer jobStatus, String remark, LocalDateTime gmtCreate, LocalDateTime gmtModified) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.cronExpression = cronExpression;
        this.jobStatus = jobStatus;
        this.remark = remark;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }
}
