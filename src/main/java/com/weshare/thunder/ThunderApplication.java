package com.weshare.thunder;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyan on 2015/4/21.
 *
 * Bootstrap for page web app
 */
@SpringBootApplication
//@EnableWebMvc
@ComponentScan
@ImportResource({"classpath*:spring/spring.xml"})
@Configuration
@EnableScheduling
public class ThunderApplication extends SpringBootServletInitializer {
    private final static Logger logger = LoggerFactory.getLogger(ThunderApplication.class);
    private @Value("${jdbc.thunder.url}") String urlThunder;
    private @Value("${jdbc.thunder.user}") String userThunder;
    private @Value("${jdbc.thunder.password}") String passwordThunder;
    private @Value("${jdbc.pay.url}") String urlPay;
    private @Value("${jdbc.pay.user}") String userPay;
    private @Value("${jdbc.pay.password}") String passwordPay;
    private @Value("${dhcp2.minIdle}") int minIdle;
    private @Value("${dhcp2.maxIdle}") int maxIdle;
    private @Value("${dhcp2.initialSize}") int initialSize;

    public static void main(String[] args) {
        logger.debug("main startup");
        SpringApplication.run(ThunderApplication.class, args);

//        ApplicationContext ctx = SpringApplication.run(ThunderApplication.class, args);
//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ThunderApplication.class);
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        logger.debug(">>> dispatcherServletRegistration");
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/");
        Map<String,String> params = new HashMap<String,String>();
        params.put("contextConfigLocation","/WEB-INF/spring/mvc.xml");
        registration.setInitParameters(params);
        registration.setLoadOnStartup(1);
//        registration.addUrlMappings("/");
        return registration;
    }

//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter() {
//        logger.debug("init characterEncodingFilter");
//        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return characterEncodingFilter;
//    }
//
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        logger.debug("setupViewResolver");
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Primary
    @Bean(name = "thunderDataSource")
    public BasicDataSource primaryDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl(urlThunder);
        basicDataSource.setUsername(userThunder);
        basicDataSource.setPassword(passwordThunder);

        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setInitialSize(initialSize);
        basicDataSource.setTimeBetweenEvictionRunsMillis(60000);
        basicDataSource.setPoolPreparedStatements(true);
        basicDataSource.setMaxOpenPreparedStatements(50);
        basicDataSource.setRemoveAbandonedTimeout(180);
        basicDataSource.setTestOnBorrow(false);
        basicDataSource.setTestOnReturn(false);
        basicDataSource.setTestWhileIdle(true);
        basicDataSource.setValidationQuery("SELECT 1");
        return basicDataSource;
    }

    @Bean(name = "payDataSource")
    public BasicDataSource secondDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl(urlPay);
        basicDataSource.setUsername(userPay);
        basicDataSource.setPassword(passwordPay);

        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setInitialSize(initialSize);
        basicDataSource.setTimeBetweenEvictionRunsMillis(60000);
        basicDataSource.setPoolPreparedStatements(true);
        basicDataSource.setMaxOpenPreparedStatements(50);
        basicDataSource.setRemoveAbandonedTimeout(180);
        basicDataSource.setTestOnBorrow(false);
        basicDataSource.setTestOnReturn(false);
        basicDataSource.setTestWhileIdle(true);
        basicDataSource.setValidationQuery("SELECT 1");
        return basicDataSource;
    }

}
