package cn.wbomb.accounting.config;

import cn.wbomb.accounting.exception.BizErrorCode;
import cn.wbomb.accounting.exception.ErrorResponse;
import cn.wbomb.accounting.exception.ServiceException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
//            saveRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            val errorResponse = ErrorResponse.builder()
                .code(BizErrorCode.NO_AUTHORIZED)
                .errorType(ServiceException.ErrorType.Client)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("No access for related url")
                .build();
            try (val write = response.getWriter();) {
                write.write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                log.error("The exception is throw");
                return false;
            }
            return false;
        }
    }
}
