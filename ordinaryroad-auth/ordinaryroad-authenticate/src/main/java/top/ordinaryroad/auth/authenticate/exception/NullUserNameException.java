package top.ordinaryroad.auth.authenticate.exception;

import org.springframework.security.core.AuthenticationException;

public class NullUserNameException extends AuthenticationException {

    private static final long serialVersionUID = 1008366430399972726L;

    public NullUserNameException(String msg, Throwable t) {
        super(msg, t);
    }

    public NullUserNameException(String msg) {
        super(msg);
    }
}
