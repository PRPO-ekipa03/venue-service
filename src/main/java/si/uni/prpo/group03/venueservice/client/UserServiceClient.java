package si.uni.prpo.group03.venueservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import si.uni.prpo.group03.venueservice.exception.UserNotFoundException;
import si.uni.prpo.group03.venueservice.exception.UserServiceException;
import si.uni.prpo.group03.venueservice.dto.UserDTO;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate, 
                             @Value("${user.service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public void verifyUserExists(Long userId) {
        try {
            String url = userServiceUrl + userId;
            restTemplate.getForEntity(url, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        } catch (Exception e) {
            throw new UserServiceException("Failed to check user existence", e);
        }
    }

    public String getUserFullName(Long userId) {
        try {
            String url = userServiceUrl + userId;
            UserDTO user = restTemplate.getForObject(url, UserDTO.class);
            if (user != null) {
                return user.getFirstName() + " " + user.getLastName();
            } else {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        } catch (Exception e) {
            throw new UserServiceException("Failed to retrieve user's full name", e);
        }
    }
}
