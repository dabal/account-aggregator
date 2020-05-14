package pl.dabal.accountaggregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.dabal.accountaggregator.config.SecurityConfig;
import pl.dabal.accountaggregator.controller.SecuredUsersController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.providers.TokenAuthenticationProvider;
import pl.dabal.accountaggregator.service.UserAuthenticationService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecuredUsersController.class)
@Import({SecurityConfig.class,
        TokenAuthenticationProvider.class,
         })
class SecuredUsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserAuthenticationService userAuthenticationService;

    @Test
    public void userWithoutCredentialsShouldNotHaveAccessToPrivateUrls() throws Exception {
        mockMvc.perform(get("/users/current").header("Authorization","token")).andExpect(status().is(401));
    }

    @Test
    public void userWithCredentialsShouldHaveAccessToPrivateUrls() throws Exception {
        User userToReturnFromMockUserRepository=User.builder()
                .email("testowy_email@email.com")
                .firstName("jan")
                .lastName("kowalski")
                .password("password")
                .token("token")
                .build();
        when(userAuthenticationService.findByToken(anyString())).thenReturn(Optional.of(userToReturnFromMockUserRepository));
        mockMvc.perform(get("/users/current/").header("Authorization","token")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void userWithBadCredentialsShouldNotHaveAccessToPrivateUrls() throws Exception {
        User userToReturnFromMockUserRepository=User.builder()
                .email("testowy_email@email.com")
                .firstName("jan")
                .lastName("kowalski")
                .password("password")
                .token("token")
                .build();
        when(userAuthenticationService.findByToken(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/current/").header("Authorization","token")).andDo(print()).andExpect(status().is(401));
    }
}
