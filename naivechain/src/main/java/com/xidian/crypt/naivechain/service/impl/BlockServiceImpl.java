package com.xidian.crypt.naivechain.service.impl;

import com.xidian.crypt.naivechain.commons.CryptUtils;
import com.xidian.crypt.naivechain.model.Block;
import com.xidian.crypt.naivechain.service.BlockService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author LvLiuWei
 * @date 2017/12/11.
 */
@Data
public class BlockServiceImpl implements BlockService {

    private Logger log = LoggerFactory.getLogger(BlockServiceImpl.class);

    private List<Block> blockChain;

    @Override
    public boolean addBlock(Block newBlock) {
        Block latestBlock = this.getLatestBlock();
        if (this.isValidNewBlock(newBlock,latestBlock)) {
            blockChain.add(newBlock);
            return true;
        }
        return false;
    }

    @Override
    public Block getFirstBlock() {
        return new Block(1, "0", System.currentTimeMillis(), "Hello Block", "aa212344fc10ea0a2cb885078fa9bc2354e55efc81be8f56b66e4a837157662e");
    }

    @Override
    public Block getLatestBlock() {
        if (blockChain.isEmpty()) {
            return null;
        }
        return blockChain.get(blockChain.size()-1);
    }

    @Override
    public Block getBlock(Integer blockIndex) {
        return null;
    }

    private boolean isValidNewBlock(Block newBlock, Block latestBlock) {
        if (latestBlock.getBlockIndex()+1 != newBlock.getBlockIndex()) {
            log.debug("invalid block blockIndex->{}.", newBlock);
            return false;
        } else if (!latestBlock.getBlockHash().equals(newBlock.getPreHash())) {
            log.debug("invalid block previous blockHash->{}.", newBlock);
            return false;
        } else {
            String hash = this.calculateHash(newBlock);
            if (!hash.equals(newBlock.getBlockHash())) {
                log.debug("calculate block blockHash error->{}.", newBlock);
                return false;
            }
        }
        return true;
    }

    private String calculateHash(Block block) {
        StringBuilder builder = new StringBuilder();
        builder.append(block.getBlockIndex())
                .append(block.getPreHash())
                .append(block.getCreateTime())
                .append(block.getBlockData());
        return CryptUtils.getSHA256(builder.toString());
    }


}
