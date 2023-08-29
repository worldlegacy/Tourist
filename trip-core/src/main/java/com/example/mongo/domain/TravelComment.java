package com.example.mongo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("travel_comment")
public class TravelComment {
    @Id
    private String id;
    //游记相关
    private Long travelId;
    private String travelTitle;
    //用户信息
    private Long userId;
    private String nickname;
    private int level;
    private String headImgUrl;
    private String city;
    //评论相关
    private String content;
    private Date createTime;
    private int type; //0 文章评论 1 评论回复
    private TravelComment refComment; //回复的评论
}
