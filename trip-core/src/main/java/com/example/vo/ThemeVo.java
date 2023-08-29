package com.example.vo;

import com.example.entity.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemeVo {
    private String themeName;
    private List<Destination> dests;
}
