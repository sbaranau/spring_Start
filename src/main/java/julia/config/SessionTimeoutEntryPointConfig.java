package julia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Точка входа в приложение, редиректит на страницу логина,
 * даёт статус 401(UNAUTHORIZED), если сессия пользователя истекла
 *
 * @author Pudul Yuriy
 */
@Configuration
public class SessionTimeoutEntryPointConfig extends LoginUrlAuthenticationEntryPoint {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";
    private static final String LOGIN_URL = "/login.html";

    public SessionTimeoutEntryPointConfig() {
        super(LOGIN_URL);
    }

    public SessionTimeoutEntryPointConfig(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        if (isAjax(request) && isFromRunningApp(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            super.commence(request, response, exception);
        }
    }

    private boolean isAjax(final HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    private boolean isFromRunningApp(final HttpServletRequest request) {
        return (request.getRequestedSessionId() != null || !request.isRequestedSessionIdValid());
    }

}
