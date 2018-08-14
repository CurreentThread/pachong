//package com.demo.modal.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import java.util.Properties;
//
//@Configuration
//public class TkMapperConfig {
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//        mapperScannerConfigurer.setBasePackage("com.demo.mapper");
//
//        //配置通用Mapper，详情请查阅官方文档
//        Properties properties = new Properties();
//        properties.setProperty("mappers","com.demo.common.IBaseMapper");
//        properties.setProperty("notEmpty", "true");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
//
//        properties.setProperty("IDENTITY", "SELECT UUID()");//使用UUID作為主鍵
//        properties.setProperty("ORDER","BEFORE");//将查询主键作为前置操作
//
//        mapperScannerConfigurer.setProperties(properties);
//
//        return mapperScannerConfigurer;
////        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
////        mapperScannerConfigurer.setBasePackage("com.demo.domain");
////        Properties propertiesMapper = new Properties();
////        //通用mapper位置，不要和其他mapper、dao放在同一个目录
////        propertiesMapper.setProperty("mappers", "com.demo.common.IBaseMapper");
////        propertiesMapper.setProperty("notEmpty", "false");
////        //主键UUID回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
////        propertiesMapper.setProperty("ORDER","BEFORE");
////        mapperScannerConfigurer.setProperties(propertiesMapper);
////        return mapperScannerConfigurer;
//    }
//
//}