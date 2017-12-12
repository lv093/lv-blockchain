package com.xidian.crypt.naivechain.mapper;

import com.xidian.crypt.naivechain.model.Block;

/**
 * @author LvLiuWei
 * @date 2017/12/12.
 */
public interface BlockInsertMapper {
    /**
     * insert ${block} into database
     * @param block
     */
    void insertBlock(Block block);

    /**
     * query block at specified index
     * @param blockIndex
     * @return
     */
    Block queryBlock(Integer blockIndex);

    /**
     * query the latest block
     * @return
     */
    Block queryLatestBlock();
}
