package com.example.mongo.domain;

import com.example.entity.Userinfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("strategy_comment")
public class StrategyComment {
    @Id
    private String id;
    //攻略相关
    private Long strategyId;
    private String strategyTitle;
    //用户相关
    private Long userId;
    private String nickName;
    private int level;
    private String headImgUrl;
    private String city;
    //评论内容相关
    private String content;
    private Date createTime;
    //点赞相关
//    private Integer thumbsupnum;
//    private List<Userinfo> thumbsupList;

}
