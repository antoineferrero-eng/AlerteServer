package AlerteServer.service;

import AlerteServer.dto.UserDTO;
import AlerteServer.entity.User;
import AlerteServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getOrCreateUserFromJwt(Jwt jwt) {
        String mail = jwt.getClaimAsString("email");

        User user = userRepository.findByMail(mail).orElseGet(() -> {
            User newUser = new User();
            newUser.setMail(mail);
            newUser.setFirstName(jwt.getClaimAsString("given_name"));
            newUser.setLastName(jwt.getClaimAsString("family_name"));
            newUser.setLevel(0);
            return userRepository.save(newUser);
        });

        String googleFirstName = jwt.getClaimAsString("given_name");
        String googleLastName = jwt.getClaimAsString("family_name");
        if ((googleFirstName != null && !googleFirstName.equals(user.getFirstName())) ||
                (googleLastName != null && !googleLastName.equals(user.getLastName()))) {
            user.setFirstName(googleFirstName);
            user.setLastName(googleLastName);
            user = userRepository.save(user);
        }

        return new UserDTO(
                user.getId(),
                user.getMail(),
                user.getFirstName(),
                user.getLastName(),
                user.getLevel());
    }
}
