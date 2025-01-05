package si.uni.prpo.group03.venueservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import si.uni.prpo.group03.venueservice.exception.UserNotFoundException;
import si.uni.prpo.group03.venueservice.exception.UserServiceException;
// PROTOTIP

@Component
public class UserServiceClient {

    private static final String USER_SERVICE_URL = "http://user-service/api/users/";

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void verifyUserExists(Long userId) {
        try {
            String url = USER_SERVICE_URL + userId;
            restTemplate.getForEntity(url, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        } catch (Exception e) {
            throw new UserServiceException("Failed to check user existence", e);
        }
    }
}
