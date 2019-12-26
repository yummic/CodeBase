package fun.clclcl.yummic.codebase.sample.springboot.mvcconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LicenseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("=========================================== LicenseFilter");
            if (isLicenseValid()) {
                filterChain.doFilter(httpServletRequest, httpServletResponse); // change is here
            } else {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "License invalid error.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while validating license in the filter", e);
            httpServletResponse.setStatus(401);
        }
    }

    private boolean isLicenseValid() {
        return false;
    }
}