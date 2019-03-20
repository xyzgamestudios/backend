package com.xwszt.backend.cfg;

/**
 * @author xwszt
 */
//@Configuration
//@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 性能分析拦截器
     * 不建议生产使用,用来观察SQL执行情况以及执行时长
     * 建议在dev、staging环境开启
     *
     * @return
     */
    //@Bean
//    public PerformanceInterceptor performanceInterceptor() {
        // 启用性能分析插件,SQL是否格式化,默认false
        // 开启格式化SQL
//        return new PerformanceInterceptor().setFormat(true);
//    }

    /**
     * 分页插件
     *
     * @return
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor(){
//        return new PaginationInterceptor();
//    }
}
