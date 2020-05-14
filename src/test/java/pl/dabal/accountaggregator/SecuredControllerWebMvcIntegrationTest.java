package pl.dabal.accountaggregator;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.dabal.accountaggregator.controller.SecuredUsersController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.providers.TokenAuthenticationProvider;
import pl.dabal.accountaggregator.repository.UserRepository;
import pl.dabal.accountaggregator.service.UserAuthenticationService;
import pl.dabal.accountaggregator.service.impl.UUIDAuthenticationService;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecuredControllerWebMvcIntegrationTest {

    @Configuration
    static class ConfClass {

        @Bean
        @Primary
        public UserAuthenticationService userAuthenticationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
            return new UUIDAuthenticationService(userRepository, bCryptPasswordEncoder);
        }

        @Bean
        @Primary
        public TokenAuthenticationProvider tokenAuthenticationProvider(UserAuthenticationService auth) {
            return new TokenAuthenticationProvider(auth);
        }

        @Bean
        @Primary
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @Primary
        UserDetailsService userDetails() {
            User userForSave = User.builder()
                    .email("testowy_email@email.com")
                    .firstName("jan")
                    .lastName("kowalski")
                    .password("password")
                    .token("token")
                    .build();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(Arrays.asList(userForSave));
            return inMemoryUserDetailsManager;
        }


    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
 
    @WithUserDetails(value = "testowy_email@email.com")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc
                .perform(get("/users/current").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
}