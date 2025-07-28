package com.maroc_ouvrage.semployee.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class SameSiteCookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(request, response);

        if (response instanceof HttpServletResponse res) {
            for (String header : res.getHeaders("Set-Cookie")) {
                if (header.startsWith("JSESSIONID") && !header.toLowerCase().contains("samesite")) {
                    res.setHeader("Set-Cookie", header + "; SameSite=None");
                }
            }
        }
    }
}
