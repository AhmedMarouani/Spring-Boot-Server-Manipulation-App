package io.getarrays.server;

import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static io.getarrays.server.enumeration.Status.*;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB", "Personal PC", "http://localhost:5432/server/image/server1.png", Server_Up));
			serverRepository.save(new Server(null, "192.168.1.58", "Fedora Linux", "16 GB", "Dell Tower","http://localhost:5432/server/image/server2.png", Server_Down));
			serverRepository.save(new Server(null, "192.168.1.21", "MS 2008", "32 GB", "Web Server", "http://localhost:5432/server/image/server3.png", Server_Up));
			serverRepository.save(new Server(null, "192.168.1.14", "Red Hat Enterprise Linux", "64 GB", "Mail Server", "http://localhost:5432/server/image/server4.png", Server_Down));
		};
	}



}