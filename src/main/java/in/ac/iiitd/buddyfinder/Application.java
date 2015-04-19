package in.ac.iiitd.buddyfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Rishav Jain on 02-04-2015.
 * MAIN CONFIGURATION FILE
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("in.ac.iiitd.buddyfinder")
@EnableMongoRepositories
public class Application extends RepositoryRestMvcConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
