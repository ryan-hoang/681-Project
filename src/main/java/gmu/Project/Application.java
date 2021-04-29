package gmu.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ServletRegistrationBean gameServletRegistration () {
		ServletRegistrationBean srb = new ServletRegistrationBean();
		srb.setServlet(new GameServlet());
		srb.setUrlMappings(Arrays.asList("/game"));
		return srb;
	}

	@Bean
	ServletRegistrationBean pregameServletRegistration () {
		ServletRegistrationBean srb = new ServletRegistrationBean();
		srb.setServlet(new PregameServlet());
		srb.setUrlMappings(Arrays.asList("/pregame"));
		return srb;
	}
}
