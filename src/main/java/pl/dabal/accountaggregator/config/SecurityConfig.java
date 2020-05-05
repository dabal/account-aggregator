package pl.dabal.accountaggregator.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.dabal.accountaggregator.filters.TokenAuthenticationFilter;
import pl.dabal.accountaggregator.providers.TokenAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(getPublicURL());
    TokenAuthenticationProvider provider;

    SecurityConfig(final TokenAuthenticationProvider provider) {
        super();
        this.provider = requireNonNull(provider);
    }

    public static RequestMatcher getPublicURL() {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        requestMatchers.add(new AntPathRequestMatcher("/public/**"));
        requestMatchers.add(new AntPathRequestMatcher("/v2/api-docs"));
        requestMatchers.add(new AntPathRequestMatcher("/swagger-resources/**"));
        requestMatchers.add(new AntPathRequestMatcher("/swagger-ui.html"));
        requestMatchers.add(new AntPathRequestMatcher("/configuration/**"));
        requestMatchers.add(new AntPathRequestMatcher("/webjars/**"));
        requestMatchers.add(new AntPathRequestMatcher("/front/**"));
        requestMatchers.add(new AntPathRequestMatcher("/assets/**"));
        requestMatchers.add(new AntPathRequestMatcher("/public"));
        requestMatchers.add(new AntPathRequestMatcher("/error"));

        RequestMatcher publicUrl = new OrRequestMatcher(requestMatchers);
        return publicUrl;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(getPublicURL());

        // Allow swagger to be accessed without authentication
//    web.ignoring().antMatchers("/v2/api-docs")
//            .antMatchers("/swagger-resources/**")
//            .antMatchers("/swagger-ui.html")//
//            .antMatchers("/configuration/**")//
//            .antMatchers("/webjars/**")//
//            .antMatchers("/front/**")//
//            .antMatchers("/assets/**")//
//            .antMatchers("/public")
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
//    .and().authorizeRequests()//
//            .antMatchers("/").permitAll()//
                .and()
                .exceptionHandling()
                // this entry point handles when you request a protected page and you are not yet
                // authenticated
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(PROTECTED_URLS)
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Bean
    TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
        final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    /**
     * Disable Spring boot automatic filter registration.
     */
    @Bean
    FilterRegistrationBean disableAutoRegistration(TokenAuthenticationFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(FORBIDDEN);
    }

  /*@Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }*/

//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  } //bean do szyfrowania hasla usera rejestrujacego sie
}
