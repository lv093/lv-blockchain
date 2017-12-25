package com.xidian.crypt.naivechain.service;

import com.xidian.crypt.naivechain.model.Block;

import java.util.List;

/**
 * @author: LvLiuWei
 * @date: 2017/12/12.
 */
public interface BlockService {

    /**
     * save block list to database
     * @param blockList
     * @return count of inserted data
     */
    Integer saveBlockList(List<Block> blockList);

    /**
     * 新增Block
     * first check validation of the block to be added.
     * if true insert the block to mysql and return true;
     * else return false.
     * @param block
     * @return
     */
    boolean addBlock(Block block);

    /**
     * query mysql to get the first block
     * @return
     */
    Block getFirstBlock();

    /**
     * query mysql to get the latest block
     * @return
     */
    Block getLatestBlock();

    /**
     * query mysql to get block at the blockIndex position
     * @param blockIndex
     * @return
     */
    Block getBlock(Integer blockIndex);
}
