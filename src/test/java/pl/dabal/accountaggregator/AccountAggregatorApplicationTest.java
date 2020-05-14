package pl.dabal.accountaggregator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@Import(SecurityConfig.class)
@WebMvcTest(SecuredUsersController.class)
//@WebMvcTest(value=SecuredUsersController.class, excludeFilters = TokenAuthenticationFilter.class)
//@ComponentScan(basePackages = "pl.dabal.accountaggregator.controller")
//@ImportAutoConfiguration
//@WebAppConfiguration
class AccountAggregatorApplicationTest {
    @Configuration
    static class ConfClass {
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
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    /*@BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // sets up Spring Security with MockMvc
                .build();
    }*/

    @Test
    public void userWithoutCredentialsShouldNotHaveAccessToPrivateUrls() throws Exception {
        mockMvc.perform(get("/users/current")).andExpect(status().is(401));

    }

    @Test
    //@WithUserDetails(value = "testowy_email@email.com")
    @WithMockUser
    public void userWithCredentialsShouldHaveAccessToPrivateUrls() throws Exception {
        mockMvc.perform(get("/users/current/")).andDo(print()).andExpect(status().isOk());

    }

}