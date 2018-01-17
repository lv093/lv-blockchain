package com.xidian.crypt.naivechain.web.rest;

import com.xidian.crypt.naivechain.model.BlockCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LvLiuWei
 * @date 2017/12/25.
 */
@RestController
@RequestMapping(value = "naive")
@Slf4j
public class BlockController {

    @PostMapping(value = "list")
    public String list(final BlockCondition condition) {
        log.info("run the list");
        return "hello first block";
    }
}
