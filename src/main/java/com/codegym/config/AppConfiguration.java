package com.codegym.config;

import com.codegym.aspect.MyLogger;
import com.codegym.formatter.CategoryFormatter;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import com.codegym.service.product.ProductService;
import org.hibernate.query.criteria.internal.predicate.NegatedPredicateWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableWebMvc
@ComponentScan("com.codegym")
@EnableTransactionManagement
@EnableJpaRepositories("com.codegym.repository")
@EnableSpringDataWebSupport
@EnableAspectJAutoProxy
public class AppConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Thymeleaf Configuration
    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }


    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //JPA configuration
    @Bean
//    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    //hibernate
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("model");
//        sessionFactory.setHibernateProperties(additionalProperties());
//        return sessionFactory;
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean c = new LocalContainerEntityManagerFactoryBean();
        c.setDataSource(dataSource());
        c.setPackagesToScan("model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        c.setJpaVendorAdapter(vendorAdapter);
        c.setJpaProperties(additionalProperties());
        return c;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/product");
        dataSource.setUsername( "root" );
        dataSource.setPassword( "123456" );
        return dataSource;
    }
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory managerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(managerFactory);
        return transactionManager;
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        super.addFormatters(registry);
        registry.addFormatter(new CategoryFormatter(applicationContext.getBean(ICategoryService.class)));
    }
    //AOP
    @Bean
    public MyLogger myLogger(){return new MyLogger();}

//    @Bean
//    public IProductService productService(){
//        return new ProductService();
//    }

}
