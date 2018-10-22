package com.epam.movie_warehouse.filter;

import com.epam.movie_warehouse.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class AccessToServiceFilter implements Filter {
    private static final String ACTIVE_INIT_PARAM_NAME = "active";
    private static final Map<String, Integer> SERVICE_MAP = new HashMap<>();
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) {
        String activeString = filterConfig.getInitParameter(ACTIVE_INIT_PARAM_NAME);
        if (activeString != null){
            active = (activeString.equalsIgnoreCase("true"));
        }
        SERVICE_MAP.put(LIST_MOVIES_ADMIN_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_MOVIES_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(MOVIE_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(SHOW_EDIT_MOVIE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(EDIT_MOVIE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(DELETE_MOVIE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(ADD_MOVIE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_MOVIE_BY_GENRE_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(LIST_MOVIE_BY_NAME_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(LOG_IN_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(AUTHORIZATION_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(REGISTRATION_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(LOG_OUT_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(SHOW_MY_USER_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(SHOW_USER_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(SHOW_USER_EDIT_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(EDIT_USER_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(LIKE_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(PUT_A_GRADE_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(DELETE_USER_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(DELETE_MY_USER_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(EDIT_USER_PASSWORD_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(LIST_USER_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(SET_LOCAL_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(UPLOAD_MOVIE_IMAGE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(UPLOAD_USER_IMAGE_URI, COMMON_USER_ROLE_ID);
        SERVICE_MAP.put(UPLOAD_HUMAN_IMAGE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_EDIT_GENRE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(EDIT_GENRE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(ADD_GENRE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(DELETE_GENRE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_GENRE_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_HUMAN_ADMIN_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(LIST_HUMAN_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(HUMAN_URI, GUEST_ROLE_ID);
        SERVICE_MAP.put(DELETE_HUMAN_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(SHOW_EDIT_HUMAN_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(EDIT_HUMAN_URI, ADMIN_ROLE_ID);
        SERVICE_MAP.put(ADD_HUMAN_URI, ADMIN_ROLE_ID);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (active) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            User user = (User) httpServletRequest.getSession().getAttribute(AUTHORIZED_USER);
            if (user == null){
                user = new User();
                user.setRoleId(0);
            }
            String reqURI = httpServletRequest.getRequestURI();
            Integer accessLevel = SERVICE_MAP.get(reqURI);
            if (accessLevel != null) {
                if (accessLevel <= user.getRoleId()) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletRequest.setAttribute(EXCEPTION, SC_FORBIDDEN);
                    httpServletResponse.sendError(SC_FORBIDDEN);
                }
            } else {
                httpServletRequest.setAttribute(EXCEPTION, SC_NOT_FOUND);
                httpServletResponse.sendError(SC_NOT_FOUND);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
