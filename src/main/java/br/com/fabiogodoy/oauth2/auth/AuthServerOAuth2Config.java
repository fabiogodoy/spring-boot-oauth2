package br.com.fabiogodoy.oauth2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import br.com.fabiogodoy.oauth2.config.AppConfig;

@EnableAuthorizationServer
@Configuration
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
    
    private final AuthenticationManager authenticationManager;
    private final AppConfig appConfig;
    
    @Autowired
    public AuthServerOAuth2Config(AuthenticationManager authenticationManager, AppConfig appConfig) {
        this.authenticationManager = authenticationManager;
        this.appConfig = appConfig;
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(appConfig.dataSource());
    }
    
 
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .approvalStoreDisabled()
                .tokenStore(tokenStore()); // Persist the tokens in the database
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(appConfig.dataSource());
    }
}
