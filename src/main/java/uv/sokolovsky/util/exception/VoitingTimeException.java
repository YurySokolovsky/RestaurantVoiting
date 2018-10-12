package uv.sokolovsky.util.exception;

import org.springframework.http.HttpStatus;

public class VoitingTimeException extends ApplicationException{
    public static final String VOTE_TIME_EXCEPTION = "exception.vote.voteTimeOver";

    public VoitingTimeException() {
        super(ErrorType.VOTE_TIME_OVER, VOTE_TIME_EXCEPTION, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
