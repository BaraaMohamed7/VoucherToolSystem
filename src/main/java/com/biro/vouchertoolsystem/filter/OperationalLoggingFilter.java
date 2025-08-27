package com.biro.vouchertoolsystem.filter;

import com.biro.vouchertoolsystem.model.OperationLog;
import com.biro.vouchertoolsystem.model.User;
import com.biro.vouchertoolsystem.model.UserPrincipal;
import com.biro.vouchertoolsystem.repository.OpsLogRepository;
import com.biro.vouchertoolsystem.repository.UserRepository;
import com.biro.vouchertoolsystem.service.JWTService;
import com.biro.vouchertoolsystem.util.DataScrubber;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
@Order(-1)
@RequiredArgsConstructor
public class OperationalLoggingFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final OpsLogRepository opsLogRepository;
    private final DataScrubber scrubber;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        OperationLog  operationLog = new OperationLog();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        operationLog.setOperation(request.getRequestURL().toString());
        operationLog.setMethod(request.getMethod());
        operationLog.setRequestTime(new Date());
        if ("POST".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
            requestWrapper.getParameterMap();
        }

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);

            if ("POST".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
                byte[] requestContent = requestWrapper.getContentAsByteArray();
                if (requestContent.length > 0) {
                    String requestBody = new String(requestContent, StandardCharsets.UTF_8);
                    requestBody = requestBody.replaceAll("\"password\"\\s*:\\s*\"[^\"]*\"", "\"password\":\"***\"");
                    operationLog.setRequest(scrubber.scrub(requestBody));

                }
            }

            byte[] responseContent = responseWrapper.getContentAsByteArray();
            if (responseContent.length > 0) {
            String responseBody = new String(responseContent, StandardCharsets.UTF_8);
            operationLog.setResponse(scrubber.scrub(responseBody));
            }
            responseWrapper.copyBodyToResponse();

            operationLog.setStatus(response.getStatus());
            operationLog.setResponseTime(new Date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().equals("/api/auth/register")) {
            String authHeader = responseWrapper.getHeader("Authorization");
            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                Long userId = jwtService.extractUserId(authHeader.replace("Bearer ", ""));
                operationLog.setUserId(userId);
            }
            } else if (authentication != null &&  authentication.isAuthenticated()) {
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                Long userId = userPrincipal.getUser().getId();
                operationLog.setUserId(userId);
            }
            opsLogRepository.save(operationLog);
        }


    }

}
