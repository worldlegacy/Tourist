package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
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
public class StrategyCatalog extends Model<StrategyCatalog> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long destId;

    private Integer state;

    private Integer seq;

    private String destName;

    //每个分类对应的攻略
    @TableField(exist = false)
    private List<Strategy> strategies = new ArrayList<>();

}
