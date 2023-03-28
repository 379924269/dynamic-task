package com.huazai.test.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "IdVo", description = "返回添加数据的id")
public class IdVo implements Serializable {
    @ApiModelProperty(value = "表id", dataType = "Integer")
    private Integer id;

    public IdVo(Integer id) {
        this.id = id;
    }
}
