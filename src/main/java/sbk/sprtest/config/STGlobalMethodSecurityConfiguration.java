package sbk.sprtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.io.IOException;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = false, proxyTargetClass = true)
@Import(AclContext.class)
public class STGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Autowired
    private AclContext aclContext;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        try {
            return aclContext.expressionHandler();
        } catch (IOException e) {
            return null;
        }
    }


}
