package com.titus.socialnetworkingsite2;

import com.titus.socialnetworkingsite2.Dto.Role;
import com.titus.socialnetworkingsite2.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
//@EnableOAuth2Sso
public class SocialNetworkingSite2Application {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkingSite2Application.class, args);
	}


	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
