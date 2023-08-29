package com.example.job;

import com.example.redis.service.ISecurityService;
import com.example.redis.vo.BlackList;
import com.example.service.IBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/18
 */
@Component
public class BlackBrushJob {
    @Autowired
    private ISecurityService securityService;
    @Autowired
    private IBlackListService blackListService;

    //存储黑名单中数据（ip,剩余时间）
    @Scheduled(cron="0/10 * * * * ?")
    void doWork(){
        //添加
        List<BlackList> blackList = securityService.addBlack();
        blackListService.saveBatch(blackList);
        System.err.println("****** 成功 ********");
    }
}
