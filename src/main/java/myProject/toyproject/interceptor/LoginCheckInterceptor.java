package myProject.toyproject.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/api/login?redirectURL=" + requestURL);
            return false;
        }
        return true;
    }
}
