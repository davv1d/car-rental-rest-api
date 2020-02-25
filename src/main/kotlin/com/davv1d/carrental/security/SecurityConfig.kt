package com.davv1d.carrental.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
        private val userPrincipalDetailService: UserPrincipalDetailService,
        private val unauthorizedHandler: JwtAuthEntryPoint,
        private val authenticationJwtAuthTokenFilter: JwtAuthTokenFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService<UserDetailsService>(userPrincipalDetailService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers(POST, "/user-registration").hasAuthority(USER_REGISTRATION)
//                .antMatchers(POST, "/user-login").permitAll()
//                .antMatchers(POST, "/customer-registration").permitAll()
//
//                .antMatchers(POST, "/location").hasAuthority(SAVE_LOCATION)
//                .antMatchers(GET, "/location").hasAuthority(GET_LOCATIONS)
//                .regexMatchers(DELETE, "\\/location\\?id=.*").hasAuthority(DELETE_LOCATION)
//
//                .antMatchers(GET, "/privilege").hasAuthority(GET_PRIVILEGES)
//
//                .antMatchers(GET, "/role").hasAuthority(GET_ROLES)
//                .antMatchers(POST, "/role").hasAuthority(SAVE_ROLE)
//                .antMatchers(PUT, "/role").hasAuthority(UPDATE_OF_ROLE_PRIVILEGES)
//                .regexMatchers(DELETE, "\\/role\\?id=.*").hasAuthority(DELETE_ROLE)
//
//                .antMatchers(GET, "/logged-user").hasAuthority(GET_LOGGED_USER)
//                .regexMatchers(GET, "\\/user\\?name=.*").hasAuthority(GET_USER_BY_NAME)
//                .regexMatchers(GET, "\\/user\\?email=.*").hasAuthority(GET_USER_BY_EMAIL)
//                .antMatchers(GET, "/user").hasAuthority(GET_USERS)
//                .regexMatchers(DELETE, "\\/user\\?name=.*").hasAuthority(DELETE_USER)
//                .regexMatchers(PUT, "\\/user\\?email=.*").hasAuthority(CHANGE_USER_EMAIL)
//
//                .antMatchers(POST, "/vehicle").hasAuthority(SAVE_VEHICLE)
//                .antMatchers(GET, "/vehicle").hasAuthority(GET_VEHICLES)
//                .regexMatchers(HttpMethod.GET, "\\/vehicle\\?registration=.*").hasAuthority(GET_VEHICLE_BY_REGISTRATION)
//                .regexMatchers(GET, "\\/vehicle\\?brand=.*").hasAuthority(GET_VEHICLES_BY_BRAND)
//                .regexMatchers(GET, "\\/vehicle\\?fuelType=.*").hasAuthority(GET_VEHICLES_BY_FUEL_TYPE)
//                .regexMatchers(GET, "\\/vehicle\\?data1=.*&locationId=.*").hasAuthority(GET_VEHICLES_BY_LOCATION)
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(authenticationJwtAuthTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}