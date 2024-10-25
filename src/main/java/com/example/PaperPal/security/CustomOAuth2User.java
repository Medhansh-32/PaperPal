package com.example.PaperPal.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;

import java.util.Map;



public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User; // The original OAuth2 user object
    private final String email;           // User's email from your database
    private final String userName;        // User's name from your database

    public CustomOAuth2User(OAuth2User oAuth2User, String email, String userName) {
        this.oAuth2User = oAuth2User;
        this.email = email;
        this.userName = userName;
    }

    @Override
    public String getName() {
        return userName; // You can return either the username or any other attribute
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes(); // Return original attributes from the OAuth2 provider
    }

    @Override
    public <A> A getAttribute(String name) {
        return oAuth2User.getAttribute(name); // Return attribute from the OAuth2 user
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities(); // Return original authorities
    }

    // Additional getters for email and username
    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
