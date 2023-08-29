package com.example;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Author Artist
 * @Description 代码生成器
 * @Date 2023/7/31
 */

public class CodeGenerator {
    public static void main(String[] args) {
        //代码生成器
        AutoGenerator ag = new AutoGenerator();
        //设置全局配置
        GlobalConfig gc = new GlobalConfig();
        //设置实体类操作数据库
        gc.setActiveRecord(true);

        //当前用户路径
        String path = System.getProperty("user.dir");
        System.out.println(path);
        //输出代码路径
        gc.setOutputDir(path + "/trip-core/src/main/java");
        //作者名
        gc.setAuthor("Artist");
        gc.setMapperName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        //设置主键的生成策略
        gc.setIdType(IdType.AUTO);
        //缓存
//        gc.setEnableCache(true);
        ag.setGlobalConfig(gc);

        //缓冲池
        DataSourceConfig dc = new DataSourceConfig();
        dc.setDriverName("com.mysql.jdbc.Driver");
        dc.setUrl("jdbc:mysql:///wolf2w47");
        dc.setUsername("root");
        dc.setPassword("root");
        ag.setDataSource(dc);

        //包路径
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example");
        ag.setPackageInfo(pc);

        //生成策略
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        //指定哪些表生成代码，不写生成所有表
//        sc.setInclude("employee");
        ag.setStrategy(sc);
        //代码生成
        ag.execute();
    }
}
