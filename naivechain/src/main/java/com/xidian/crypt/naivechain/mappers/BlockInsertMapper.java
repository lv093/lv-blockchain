package com.xidian.crypt.naivechain.mappers;

import com.xidian.crypt.naivechain.model.Block;
import org.springframework.stereotype.Service;

/**
 * @author LvLiuWei
 * @date 2017/12/12.
 */
@Service
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
