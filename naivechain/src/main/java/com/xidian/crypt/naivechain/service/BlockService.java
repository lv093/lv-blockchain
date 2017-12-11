package com.xidian.crypt.naivechain.service;

import com.xidian.crypt.naivechain.commons.CryptUtils;
import com.xidian.crypt.naivechain.model.Block;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: LvLiuWei
 * @date: 2017/12/11.
 */
@Data
public class BlockService {

    private Logger log = LoggerFactory.getLogger(BlockService.class);

    private List<Block> blockChain;


    public void addBlock(Block newBlock) {
        Block latestBlock = this.getLatestBlock();
        if (this.isValidNewBlock(newBlock,latestBlock)) {
            blockChain.add(newBlock);
        }
    }

    private Block getLatestBlock() {
        if (blockChain.isEmpty()) {
            return null;
        }
        return blockChain.get(blockChain.size()-1);
    }

    private boolean isValidNewBlock(Block newBlock, Block latestBlock) {
        if (latestBlock.getIndex()+1 != newBlock.getIndex()) {
            log.debug("invalid block index->{}.", newBlock);
            return false;
        } else if (!latestBlock.getHash().equals(newBlock.getPreHash())) {
            log.debug("invalid block previous hash->{}.", newBlock);
            return false;
        } else {
            String hash = this.calculateHash(newBlock);
            if (!hash.equals(newBlock.getHash())) {
                log.debug("calculate block hash error->{}.", newBlock);
                return false;
            }
        }
        return true;
    }

    private String calculateHash(Block block) {
        StringBuilder builder = new StringBuilder();
        builder.append(block.getIndex())
                .append(block.getPreHash())
                .append(block.getTimestamp())
                .append(block.getData());
        return CryptUtils.getSHA256(builder.toString());
    }


}
