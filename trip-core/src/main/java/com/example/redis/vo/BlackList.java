package com.example.redis.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlackList extends Model<BlackList> {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String ipKey;
    private Long openTime;
}
