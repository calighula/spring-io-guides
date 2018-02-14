package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@PropertySource ("classpath:security.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${sgx.user.name}")
	private String name;
	
	@Value("${sgx.user.password}")
	private String password;
	
	@Value("${sgx.user.role}")
	private String role;	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		configureInMemory(auth);
	}
	
	protected void configureInMemory(AuthenticationManagerBuilder auth) throws Exception {
	    auth.inMemoryAuthentication()
	    	.withUser(name)
	    	.password(password)
	    	.roles(role);
	}

	protected void configureWithLDAP(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
				.contextSource()
					.url("ldap://localhost:8389/dc=springframework,dc=org")
					.and()
				.passwordCompare()
					.passwordEncoder(new LdapShaPasswordEncoder())
					.passwordAttribute("userPassword");
	}
	
}
