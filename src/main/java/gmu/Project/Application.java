package gmu.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ServletRegistrationBean myServletRegistration () {
		ServletRegistrationBean srb = new ServletRegistrationBean();
		srb.setServlet(new GameServlet());
		srb.setUrlMappings(Arrays.asList("/game"));
		return srb;
	}

}
