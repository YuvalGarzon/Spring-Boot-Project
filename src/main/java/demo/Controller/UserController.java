package demo.Controller;

import demo.Exception.BadRequestException;
import demo.BusinessLogic.UserLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import demo.Exception.NotFoundException;

import java.util.Optional;

@RestController
@RequestMapping("/ambient-intelligence/users")
public class UserController {

    private final UserLogic userLogic;

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary createUser(@RequestBody UserBoundary user) throws BadRequestException {
        return this.userLogic.createUser(user);
    }

    @GetMapping(path = "/login/{systemID}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary login(@PathVariable("systemID") String systemID, @PathVariable("userEmail") String userEmail) {
        Optional<UserBoundary> optionalUser = this.userLogic.getUserByEmail(userEmail);
        UserBoundary user = optionalUser
                .orElseThrow(() -> new NotFoundException("User with email " + userEmail + " not found"));

        if (!user.getUserId().getSystemID().equals(systemID)) {
            throw new NotFoundException("SystemID does not match");
        }

        return user;
    }

    @PutMapping(path = "/{systemID}/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable("systemID") String systemID, @PathVariable("userEmail") String userEmail, @RequestBody UserBoundary update) {
        this.userLogic.updateUser(systemID, userEmail, update);
    }

}
