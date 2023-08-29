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
public class Banner extends Model<Banner> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long refid;

    private String title;

    private String subtitle;

    private String coverUrl;

    private Integer state;

    private Integer seq;

    private Integer type;

}
