package com.xidian.crypt.naivechain.model;

import lombok.Data;

/**
 * @author: LvLiuWei
 * @date: 2017/12/11.
 */
@Data
public class Block {
    private Integer index;
    private String preHash;
    private Long timestamp;
    private String data;
    private String hash;

    public Block(Integer index, String preHash, Long timestamp, String data, String hash) {
        this.index = index;
        this.preHash = preHash;
        this.timestamp = timestamp;
        this.data = data;
        this.hash = hash;
    }
}
