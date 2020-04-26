package curso.springboot.springboot;

import java.security.MessageDigest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "curso.springboot.model")
@ComponentScan(basePackages = {"curso.*"})
@EnableJpaRepositories(basePackages = {"curso.springboot.repository"})
@EnableTransactionManagement
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		/*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String text = "123";
		String result = "";
		for(int i = 0; i < 10; i++) {
			 result = encoder.encode(text).;
			System.out.println(result);
		}*/
		 
	}

}
