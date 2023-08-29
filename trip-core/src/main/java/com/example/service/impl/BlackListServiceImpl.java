package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.BlackListMapper;
import com.example.redis.vo.BlackList;
import com.example.service.IBlackListService;
import org.springframework.stereotype.Service;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/18
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements IBlackListService {

}
