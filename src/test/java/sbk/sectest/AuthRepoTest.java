package sbk.sectest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import sbk.sprtest.application.WebConfig;
import sbk.sprtest.config.DataBaseConfig;
import sbk.sprtest.config.WebAppInitializer;
import sbk.sprtest.entity.PrincipalEntity;
import sbk.sprtest.repo.PrincipalRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, DataBaseConfig.class})
public class AuthRepoTest {

    @Autowired
    private PrincipalRepository principalRepository;

    @Test
    public void baseTest() {
        assertThat(principalRepository, notNullValue());
        assertThat(principalRepository, not(nullValue()));
    }

    @Test
    @Transactional
    public void getUserWithId1() {
        PrincipalEntity principal1 = principalRepository.getOne(1L);
        PrincipalEntity principal2 = principalRepository.findPrincipalById(1L);
        PrincipalEntity principal3 = principalRepository.findPrincipalByLogin("sobik");
        assertThat(principal1.getRoles(), hasSize(3));
        assertThat(principal1, equalTo(principal2));
        assertThat(principal1, equalTo(principal3));
    }
}
