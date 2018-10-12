package uv.sokolovsky.util.exception;

import org.springframework.http.HttpStatus;

public class PasswordException extends ApplicationException{
    public static final String WRONG_OLD_PASSWORD ="exception.password.wrong";

    public PasswordException() {
        super(ErrorType.VALIDATION_ERROR, WRONG_OLD_PASSWORD, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

