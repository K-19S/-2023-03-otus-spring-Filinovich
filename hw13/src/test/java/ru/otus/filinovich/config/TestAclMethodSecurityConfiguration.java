package ru.otus.filinovich.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@TestConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TestAclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
}

