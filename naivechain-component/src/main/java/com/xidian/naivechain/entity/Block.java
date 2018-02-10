package com.xidian.naivechain.entity;

import lombok.Data;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Data
public class Block {

    /**
     * use UUID as an index
     */
    private String index;

    private Integer version;

    private Long timestamp;

    private Long nonce;

    private Transaction transaction;
}
