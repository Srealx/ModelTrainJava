package com.binninfo.model_train.vo;
import lombok.Data;

/**
 * @author cheng
 * @since 2023/9/24
 */
@Data
public class FeignAreaVO {
    private Integer code;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 1.省 2.市 3.区/县
     */
    private String areaType;
    /**
     * 上级id
     */
    private Integer parentCode;

    private Integer id;

}
