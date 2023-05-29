package com.example.weatherbot;

import com.example.weatherbot.model.User;
import com.example.weatherbot.repository.UserRepository;
import com.example.weatherbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public abstract class TestsContext {
}
