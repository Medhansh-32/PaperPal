package com.example.PaperPal.service;
import com.example.PaperPal.entity.Users;
import com.example.PaperPal.repository.UserRepository;
import com.example.PaperPal.security.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {



        private UserRepository userRepository; // Your user repository for DB operations

        public CustomOAuth2UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            // Load user from GitHub
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            String email = extractEmail(oAuth2User.getAttributes(), userRequest);
            String username = (String) oAuth2User.getAttributes().get("login"); // GitHub username
            log.info(email);
            if(email ==null){
                throw new OAuth2AuthenticationException("email is null");
            }
            // Retrieve existing user or create a new one
            Users user = userRepository.findByEmail(email);
            // Create new user if not found
            if(user==null){
             userRepository.save(Users.builder().userName(username).email(email).build());
            }
            // Return a CustomOAuth2User
            return new CustomOAuth2User(oAuth2User, user.getEmail(), user.getUserName());
        }


        private Users createUser(String username, String email) {
            Users newUser = new Users();
            newUser.setUserName(username);
            newUser.setEmail(email);
            // You might not want to set a password here as it's an OAuth login
            return userRepository.save(newUser);
        }
    private String extractEmail(Map<String, Object> attributes, OAuth2UserRequest userRequest) {
        // Check if the email is present in the initial attributes
        if (attributes.containsKey("email") && attributes.get("email")!=null) {
            log.info(attributes.get("email").toString());
            return (String) attributes.get("email");
        }

        // Make an additional request to get user emails from the GitHub API
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        String emailsApiUrl = "https://api.github.com/user/emails";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make a GET request to fetch the user's email addresses
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    emailsApiUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            // Check if the response is successful and contains email data
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                System.out.println(response);
                for (Map<String, Object> emailInfo : response.getBody()) {
                    String email = (String) emailInfo.get("email");
                    System.out.println(emailInfo);
                    Boolean verified = (Boolean) emailInfo.get("verified");
                    if (verified != null && verified) { // Check if the email is verified
                        return email; // Return the first verified email found
                    }
                }
            }
        } catch (RestClientException e) {
            // Handle any exceptions that may occur during the API call
            System.err.println("Error fetching emails from GitHub: " + e.getMessage());
        }

        // Fallback if no verified email found
        return null;
    }
}

