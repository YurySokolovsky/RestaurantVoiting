package uv.sokolovsky.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uv.sokolovsky.AuthorizedUser;
import uv.sokolovsky.model.User;
import uv.sokolovsky.service.UserService;
import uv.sokolovsky.util.exception.PasswordException;

@Component
public class PasswordUtil {
    //public boolean isValid = true;

    @Autowired
    private UserService servise;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validatePassword (String password) {
        User updated = servise.get(AuthorizedUser.id());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        DelegatingPasswordEncoder dpe = (DelegatingPasswordEncoder) passwordEncoder;
        dpe.setDefaultPasswordEncoderForMatches(encoder);
        return dpe.matches(password,updated.getPassword());
    }

    public boolean validatePassword (String oldPassword, String newPassword) throws PasswordException{
            if (!validatePassword(oldPassword)) {
                throw new PasswordException();
            }
            if (newPassword.length()<5 || newPassword.length()>32) {
                throw new PasswordException();
            }
        return true;
    }
}
