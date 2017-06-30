package cn.sz.lili.module;

import org.springframework.stereotype.Repository;

/**
 * Created by chenlei2 on 2017/6/29 0029.
 */
@Repository
public interface PromptMsgMapper {
     PromptMsg getRandomPromptMsg();
}
