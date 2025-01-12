package si.uni.prpo.group03.venueservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User Data Transfer Object representing user details.")
public class UserDTO {

    @Schema(description = "The username of the user", example = "john_doe")
    private final String username;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private final String email;

    @Schema(description = "First name of the user", example = "John")
    private final String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private final String lastName;

    public UserDTO(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
