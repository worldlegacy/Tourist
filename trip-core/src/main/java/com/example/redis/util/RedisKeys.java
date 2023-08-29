package com.example.redis.util;

import com.example.util.Consts;
import lombok.Getter;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/3
 */
@Getter
public enum RedisKeys {
    BLACK("black",-1l),
    BLACK_BRUSH("black_brush",Consts.BRUSH_TIME * 24l),
//    BLACK_BRUSH_TWO("black_brush_two",Consts.BRUSH_TIME * 24 * 30l),
    BRUSH("brush",Consts.BRUSH_TIME * 60l),
    STRATEGY_THUMBSUP("strategy_thumbsup",-1l),
    USER_STRATEGY_FAVOR("user_strategy_favor",-1l),
    STRATEGY_STATIS_VO("strategy_statis_vo",-1l),
    USER_LOGIN_TOKEN("user_login_token" , Consts.USER_TOKEN_TIME * 60l),
    REGIST_VERFY_CODE("regist_verify_code", Consts.VERIFY_CODE_TIME * 60l);

    private String prefix; //key的前缀
    private Long time; //key的过期时间

    private RedisKeys(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    //拼接key
    public String join(String... values) {
        //多线程安全
        StringBuffer sb = new StringBuffer();
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }
}
