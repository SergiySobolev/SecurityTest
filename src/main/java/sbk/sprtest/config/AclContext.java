package sbk.sprtest.config;

import net.sf.ehcache.CacheManager;
import org.hibernate.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.io.IOException;

@Configuration
@Import(DataBaseConfig.class)
public class AclContext {

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler() throws IOException {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new AclPermissionEvaluator(aclService()));
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public JdbcMutableAclService aclService() throws IOException{
        JdbcMutableAclService aclService = new JdbcMutableAclService(dataBaseConfig.dataSource(),
                lookupStrategy(), aclCache());
        return aclService;
    }

    @Bean
    public BasicLookupStrategy lookupStrategy() throws IOException {
        BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(dataBaseConfig.dataSource(),
                aclCache(), aclAuthorizationStrategy(), auditLogger());
        return lookupStrategy;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public CacheManager ehCacheCacheManager() {
        return ehCacheManagerFactoryBean().getObject();
    }


    @Bean(name = "ehcache")
    public EhCacheFactoryBean ehCacheFactoryBean() throws CacheException, IOException {
        EhCacheFactoryBean factory = new EhCacheFactoryBean();
        factory.setCacheManager(ehCacheCacheManager());
        factory.setCacheName("aclCache");
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public EhCacheBasedAclCache aclCache() throws IOException {
        EhCacheBasedAclCache aclCache = new EhCacheBasedAclCache(ehCacheFactoryBean().getObject());
        return aclCache;
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy(){
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_USER > ROLE_VISITOR"
        );
        return roleHierarchy;
    }

    @Bean
    public AuditLogger auditLogger(){
        return new ConsoleAuditLogger();
    }


}
