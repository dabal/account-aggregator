package pl.dabal.accountaggregator.repository;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.dabal.accountaggregator.model.User;

import javax.validation.constraints.AssertTrue;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp(){
       user=User.builder()
               .email("testowy_email@email.com")
               .firstName("jan")
               .lastName("kowalski")
               .password("password")
               .token("token")
               .build();
       entityManager.persist(user);
       entityManager.flush();

    }

    @Test
    void findByEmailWhichIsPresentInDBShouldReturnOptionalWithPresentUser() {
        Optional<User> repositoryUser=userRepository.findByEmail("testowy_email@email.com");
        Assert.assertEquals(user, repositoryUser.get());

    }

    @Test
    void findByEmailWhichIsNotPresentInDBShouldReturnEmptyOptional(){
        Optional<User> repositoryUser=userRepository.findByEmail("invalid_email@email.com");
        Assert.assertFalse(repositoryUser.isPresent());
    }

    @Test
    void findByTokenWhichIsPresentInDBShouldReturnOptionalWithPresentUser() {
        Optional<User> repositoryUser=userRepository.findByToken("token");
        Assert.assertEquals(user, repositoryUser.get());

    }

    @Test
    void findByTokenWhichIsNotPresentInDBShouldReturnEmptyOptional(){
        Optional<User> repositoryUser=userRepository.findByToken("invalid_token");
        Assert.assertFalse(repositoryUser.isPresent());
    }


    @Test
    void findByToken() {
        Optional<User> repositoryUser=userRepository.findByToken("token");
        Assert.assertEquals(user, repositoryUser.get());
    }

    @Test
    void saveShouldSaveUserToDB(){
        //given
        User userForSave=User.builder()
                .email("testowy_email2@email.com")
                .firstName("jan")
                .lastName("kowalski")
                .password("password")
                .token("token")
                .build();
        //when
        userRepository.save(userForSave);
        //then
        User userFromRepository=entityManager.find(User.class,userForSave.getId());
        Assert.assertEquals(userForSave,userFromRepository);

    }
}