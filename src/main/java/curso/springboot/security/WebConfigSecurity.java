package curso.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	private ImplementacaoUserDatailsService implementacaoUserDatailsService;
	
	@Override //configurar as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable() //Desativa as configurações padrão de memória do Spring.
			.authorizeRequests() //Permite restringir acessos
			.antMatchers(HttpMethod.GET, "/").permitAll()// Qualquer usuário acessa a pagina inicial
			.anyRequest().authenticated()
			.and().formLogin().permitAll() //permite qualquer usuário
			.and().logout() //Mapeia URL de Logout e invalida usuário autenticado
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override // Cria autenticaÃ§Ã£o do usuÃ¡rio com banco de dados ou em memÃ³ria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDatailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override //Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
}
