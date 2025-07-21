package com.binninfo.model_train.vo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author chengang
 * @Date 2025/6/4
 */
@Data
public class InfoQueryParam {
    @NotNull(message = "id必传")
    private Integer id;
}
