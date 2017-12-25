package com.xidian.crypt.naivechain.model;

import lombok.Data;

import java.util.Date;

/**
 * @author: LvLiuWei
 * @date: 2017/12/11.
 */
@Data
public class Block {
    private Integer blockIndex;
    private String blockHash;
    private String blockData;
    private Long createTime;
    private Integer preIndex;
    private String preHash;
    private Date saveTime;

    public Block(Integer blockIndex, String preHash, Long createTime, String blockData, String blockHash) {
        this.blockIndex = blockIndex;
        this.preHash = preHash;
        this.createTime = createTime;
        this.blockData = blockData;
        this.blockHash = blockHash;
    }
}
