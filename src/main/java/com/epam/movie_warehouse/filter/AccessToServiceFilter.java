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
    private static Map<String, Integer> serviceMap = new HashMap<>();
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) {
        String activeString = filterConfig.getInitParameter(ACTIVE_INIT_PARAM_NAME);
        if (activeString != null){
            active = (activeString.equalsIgnoreCase("true"));
        }
        serviceMap.put(LIST_MOVIES_ADMIN_URI, ADMIN);
        serviceMap.put(LIST_MOVIES_URI,GUEST);
        serviceMap.put(MOVIE_URI, GUEST);
        serviceMap.put(SHOW_EDIT_MOVIE_URI, ADMIN);
        serviceMap.put(EDIT_MOVIE_URI, ADMIN);
        serviceMap.put(DELETE_MOVIE_URI, ADMIN);
        serviceMap.put(ADD_MOVIE_URI, ADMIN);
        serviceMap.put(LIST_MOVIE_BY_GENRE_URI, GUEST);
        serviceMap.put(LIST_MOVIE_BY_NAME_URI, GUEST);
        serviceMap.put(LOG_IN_URI,GUEST);
        serviceMap.put(AUTHORIZATION_URI, GUEST);
        serviceMap.put(REGISTRATION_URI, GUEST);
        serviceMap.put(LOG_OUT_URI, COMMON_USER);
        serviceMap.put(SHOW_MY_USER_URI, COMMON_USER);
        serviceMap.put(SHOW_USER_URI, ADMIN);
        serviceMap.put(SHOW_USER_EDIT_URI, COMMON_USER);
        serviceMap.put(EDIT_USER_URI, COMMON_USER);
        serviceMap.put(LIKE_URI, COMMON_USER);
        serviceMap.put(PUT_A_GRADE_URI, COMMON_USER);
        serviceMap.put(DELETE_USER_URI, ADMIN);
        serviceMap.put(DELETE_MY_USER_URI, COMMON_USER);
        serviceMap.put(EDIT_USER_PASSWORD_URI, COMMON_USER);
        serviceMap.put(LIST_USER_URI, ADMIN);
        serviceMap.put(SET_LOCAL_URI, GUEST);
        serviceMap.put(UPLOAD_MOVIE_IMAGE_URI, ADMIN);
        serviceMap.put(UPLOAD_USER_IMAGE_URI, COMMON_USER);
        serviceMap.put(UPLOAD_HUMAN_IMAGE_URI, ADMIN);
        serviceMap.put(LIST_EDIT_GENRE_URI, ADMIN);
        serviceMap.put(EDIT_GENRE_URI, ADMIN);
        serviceMap.put(ADD_GENRE_URI, ADMIN);
        serviceMap.put(DELETE_GENRE_URI, ADMIN);
        serviceMap.put(LIST_GENRE_URI, ADMIN);
        serviceMap.put(LIST_HUMAN_ADMIN_URI, ADMIN);
        serviceMap.put(LIST_HUMAN_URI, GUEST);
        serviceMap.put(HUMAN_URI, GUEST);
        serviceMap.put(DELETE_HUMAN_URI, ADMIN);
        serviceMap.put(SHOW_EDIT_HUMAN_URI, ADMIN);
        serviceMap.put(EDIT_HUMAN_URI, ADMIN);
        serviceMap.put(ADD_HUMAN_URI, ADMIN);
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
            Integer accessLevel = serviceMap.get(reqURI);
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
