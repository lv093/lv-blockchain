package com.xidian.naivechain.beans;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author LvLiuWei
 * @date 2018/2/9
 */
@Data
public class Person {
    private String name;
    private Car car;
    private List<Car> carList;
    private Map<String, String> description;


}
