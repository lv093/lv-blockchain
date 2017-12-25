package com.xidian.crypt.naivechain.model;

import lombok.Data;

import java.util.List;

/**
 * @author: LvLiuWei
 * @date: 2017/12/12.
 */
@Data
public class BlockChain {
    private List<Block> blockChain;

    public BlockChain(List<Block> blockChain) {
        this.blockChain = blockChain;
    }

    public void addBlock(Block block) {
        blockChain.add(block);
    }

    private boolean validBlock(Block block) {

        return false;
    }
}
