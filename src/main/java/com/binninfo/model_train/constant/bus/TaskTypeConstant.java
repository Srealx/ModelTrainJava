package com.binninfo.model_train.constant.bus;

import java.util.HashSet;
import java.util.Set;

/**
 * 任务类型常量
 * @Author chengang
 * @Date 2025/7/9
 */
public class TaskTypeConstant {
    public static final int TRAIN = 0;
    public static final String TRAIN_STRING = "0";

    public static final int VAL = 1;
    public static final String VAL_STRING = "1";


    public static final int ARBITRARY = 99;


    public static Set<Integer> allTypes(){
        HashSet<Integer> objects = new HashSet<>();
        objects.add(TRAIN);
        objects.add(VAL);
        return objects;
    }
}
