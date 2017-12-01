package julia.config;

import julia.security.utils.AuthenticationUtils;
import julia.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Хандлер для редиректа со страницы логина на главную страницу {/profile/task.html}
 *
 * @author Pudul Yuriy
 */
@Configuration
public class AuthSuccessRedirectUrlHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthSuccessRedirectUrlHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        handle(request, response);
        clearAuthenticationAttributes(request);
    }

    private void handle(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
         String targetUrl;

        if(userService.get(AuthenticationUtils.getAuthentication().getId()).getDefaultPassword()){
            targetUrl = "/app#/settings/password";
        } else {
            targetUrl = "/app#/profile/task/list";
        }

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
