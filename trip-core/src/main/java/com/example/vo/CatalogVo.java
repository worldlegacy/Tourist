package com.example.vo;
import com.example.entity.StrategyCatalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Artist
 * @Description vo对象类：后端没有，前端需求
 * @Date 2023/8/7
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatalogVo {
    private String destName;
    private List<StrategyCatalog> catalogList;
}
