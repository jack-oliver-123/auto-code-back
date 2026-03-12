package com.autocode.autocodeback.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;

public class Codegen {
    private final static String YAML_PATH = "application.yml";
    private final static String DATASOURCE = "spring.datasource";
    private final static String URL = "url";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    private final static String SET_ROOT = "com.test";
    
    /**
     * Mybatis Flux 代码生成器
     */
    public static void main(String[] args) {
        // 先获取相关配置
        Dict dict = YamlUtil.loadByPath(YAML_PATH);
        Map<String, Object> map = dict.getByPath(DATASOURCE);
        String url = String.valueOf(map.get(URL));
        String username = String.valueOf(map.get(USERNAME));
        String password = String.valueOf(map.get(PASSWORD));
        
        // 配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        GlobalConfig globalConfig = createGlobalConfig();
        
        
        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);
        
        // 生成代码
        generator.generate();
    }
    
    public static GlobalConfig createGlobalConfig() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        
        // 设置根包
        globalConfig.getPackageConfig()
                .setBasePackage(SET_ROOT);
        
        // 设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setTablePrefix("tb_")
                .setGenerateTable("tb_user", "tb_account_session")
                .setLogicDeleteColumn("is_delete");
        
        // 设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(21);
        
        // 设置生成 mapper
        globalConfig.enableMapper();
        
        // 设置生成 mapperXml
        globalConfig.enableMapperXml();
        
        // 设置生成 service
        globalConfig.enableService();
        
        // 设置生成 serviceImpl
        globalConfig.enableServiceImpl();
        
        // 设置生成 controller
        globalConfig.enableController();
        
        
        // 设置生成 mapper
        globalConfig.enableMapper();
        
        globalConfig.getJavadocConfig()
                .setAuthor("Jack")
                .setSince("");
        
        return globalConfig;
    }
}