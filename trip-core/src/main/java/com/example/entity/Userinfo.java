package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
//@ApiModel(value = "用户实例",description = "存储用户信息")
public class Userinfo extends Model<Userinfo> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
//    @ApiModelProperty(value = "用户id",dataType = "Long",required = true)
    private Long id;

//    @ApiModelProperty(value = "用户昵称",dataType = "String",required = true)
    private String nickname;

//    @ApiModelProperty(value = "用户手机号",dataType = "String",required = true)
    private String phone;

//    @ApiModelProperty(value = "用户邮箱",dataType = "String",required = true)
    private String email;

//    @ApiModelProperty(value = "用户密码",dataType = "String",required = true)
    private String password;

//    @ApiModelProperty(value = "用户性别",dataType = "Integer",required = true)
    private Integer gender;

//    @ApiModelProperty(value = "用户等级",dataType = "Integer",required = true)
    private Integer level;

//    @ApiModelProperty(value = "用户所在城市",dataType = "String",required = true)
    private String city;

//    @ApiModelProperty(value = "用户头像",dataType = "String",required = true)
    private String headImgUrl;

//    @ApiModelProperty(value = "用户介绍",dataType = "String",required = true)
    private String info;

//    @ApiModelProperty(value = "用户状态",dataType = "Integer",required = true)
    private Integer state;
}
