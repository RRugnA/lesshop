package br.com.fatec.les.crudsimples;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/css/**", "/img/**", "/js/**")
				.permitAll()
				
//			Site
			.antMatchers("/lesshop/**")
				.permitAll()
			.antMatchers("/baseIndex/**")
				.permitAll()
			.antMatchers("/produto/**")
				.permitAll()
//			.antMatchers("/carrinho/**")
//				.permitAll()
//			.antMatchers("/carrinho-login/**")
//				.permitAll()
				
//			Cadastro
			.antMatchers("/cliente/cadastro-login**")
				.permitAll()
				
			.anyRequest().
				authenticated()
		.and()
		.formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/login-home", true)
            .permitAll()
		)

		.logout(logout -> {
			logout.logoutUrl("/logout")
				.logoutSuccessUrl("/lesshop");
		});
		http.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
//		UserDetails user =
//				User.builder()
//					.username("admin")
//					.password(encoder.encode("admin"))
//					.roles("ADM")
//					.build();
		
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.passwordEncoder(encoder);
//			.withUser(user);
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}
