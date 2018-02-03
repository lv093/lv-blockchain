package com.xidian.naivechain.entity;

import lombok.Data;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Data
public class Block {

    private Integer version;

    private String index;

    private Long timestamp;

    private Long nonce;


    private Transaction transaction;
}
