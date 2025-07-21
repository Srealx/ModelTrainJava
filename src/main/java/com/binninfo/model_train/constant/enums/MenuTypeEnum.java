package com.binninfo.model_train.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: brucezhang
 * @Date: 2023/07/20/17:07
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum MenuTypeEnum {
    FIRST_LEVEL(1, "一级菜单"),
    SECOND_LEVEL(2, "二级菜单"),
    THREE_LEVEL(3, "三级菜单"),
    ;
    private final int value;
    private final String description;
}
