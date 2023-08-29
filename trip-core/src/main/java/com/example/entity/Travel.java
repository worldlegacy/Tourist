package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Travel extends Model<Travel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long destId;

    private String destName;

    private Long authorId;

    @TableField(exist = false)
    private Userinfo author;

    private String title;

    private String summary;

    private String coverUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date travelTime;

    private Integer avgConsume;

    private Integer day;

    private Integer person;

    private Date createTime;

    private Date releaseTime;

    private Date lastUpdateTime;

    private Integer ispublic;

    private Integer viewnum;

    private Integer replynum;

    private Integer favornum;

    private Integer sharenum;

    private Integer thumbsupnum;

    private Integer state;

    @TableField(exist = false)
    private TravelContent content;

    //硬适配
    void setPerExpend(Integer perExpend){
        this.avgConsume = perExpend;
    }
    int getPerExpend(){
        return this.avgConsume;
    }
}
