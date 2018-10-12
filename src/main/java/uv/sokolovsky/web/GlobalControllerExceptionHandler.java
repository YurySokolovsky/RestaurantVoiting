package uv.sokolovsky.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import uv.sokolovsky.util.ValidationUtil;
import uv.sokolovsky.util.exception.ApplicationException;
import uv.sokolovsky.util.exception.ErrorType;
import uv.sokolovsky.util.exception.PasswordException;
import uv.sokolovsky.web.util.MessageUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView wrongRequest(HttpServletRequest req, NoHandlerFoundException e) throws Exception {
        return logAndGetExceptionView(req, e, false, ErrorType.WRONG_REQUEST, null);
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView applicationErrorHandler(HttpServletRequest req, ApplicationException appEx) throws Exception {
        return logAndGetExceptionView(req, appEx, true, appEx.getType(), messageUtil.getMessage(appEx));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return logAndGetExceptionView(req, e, true, ErrorType.APP_ERROR, null);
    }

    @ExceptionHandler(PasswordException.class)
    public void passwordErrorHandler(HttpServletRequest req, ApplicationException appEx) throws Exception {
        log.warn("We are in passwordErrorHandler for GlobalControllerExceptionHandler");
        ScriptEngineManager manager=new ScriptEngineManager();
        ScriptEngine javaScriptEngine= manager.getEngineByName("JavaScript");
        javaScriptEngine.eval("var msgl='Ошибка'; var msgl1 = msgl + ' пароля'; println(msgl1);");
    }

    private ModelAndView logAndGetExceptionView(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String msg) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("typeMessage", messageUtil.getMessage(errorType.getErrorCode()));
        mav.addObject("exception", rootCause);
        mav.addObject("message", msg != null ? msg : ValidationUtil.getMessage(rootCause));
        return mav;
    }
}
