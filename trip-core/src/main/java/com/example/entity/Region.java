package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class Region extends Model<Region> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String sn;

    private Boolean ishot;

    private Integer seq;

    private String info;

    private String refIds;
}
