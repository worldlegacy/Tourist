package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class Strategy extends Model<Strategy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long destId;
    private String destName;
    private Long themeId;
    private String themeName;
    private Long catalogId;
    private String catalogName;
    private String title;
    private String subTitle;
    private String summary;
    private String coverUrl;
    private Date createTime;
    private Integer isabroad;
    private Integer viewnum;
    private Integer replynum;
    private Integer favornum;
    private Integer sharenum;
    private Integer thumbsupnum;
    private Integer state;

    @TableField(exist = false)
    private StrategyContent content;
    @TableField(exist = false)
    private List<Destination> dests = new ArrayList<>();
}
