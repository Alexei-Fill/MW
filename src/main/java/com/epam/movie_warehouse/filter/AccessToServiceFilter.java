package com.epam.movie_warehouse.filter;

import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.enumiration.UserRole;

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
    private static final Map<String, Integer> ACCESS_MAP = new HashMap<>();
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) {
        String activeString = filterConfig.getInitParameter(ACTIVE_INIT_PARAM_NAME);
        if (activeString != null) {
            active = (activeString.equalsIgnoreCase("true"));
        }
        ACCESS_MAP.put(LIST_MOVIES_ADMIN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(SHOW_EDIT_MOVIE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(EDIT_MOVIE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(DELETE_MOVIE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(ADD_MOVIE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(SHOW_USER_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(DELETE_USER_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(LIST_USER_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(UPLOAD_MOVIE_IMAGE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(UPLOAD_HUMAN_IMAGE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(LIST_EDIT_GENRE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(EDIT_GENRE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(ADD_GENRE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(DELETE_GENRE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(LIST_GENRE_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(LIST_HUMAN_ADMIN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(DELETE_HUMAN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(SHOW_EDIT_HUMAN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(EDIT_HUMAN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(ADD_HUMAN_URI, UserRole.ADMIN.getId());
        ACCESS_MAP.put(LOG_OUT_URI, UserRole.USER.getId());
        ACCESS_MAP.put(SHOW_MY_USER_URI, UserRole.USER.getId());
        ACCESS_MAP.put(SHOW_USER_EDIT_URI, UserRole.USER.getId());
        ACCESS_MAP.put(EDIT_USER_URI, UserRole.USER.getId());
        ACCESS_MAP.put(LIKE_URI, UserRole.USER.getId());
        ACCESS_MAP.put(PUT_A_GRADE_URI, UserRole.USER.getId());
        ACCESS_MAP.put(DELETE_MY_USER_URI, UserRole.USER.getId());
        ACCESS_MAP.put(EDIT_USER_PASSWORD_URI, UserRole.USER.getId());
        ACCESS_MAP.put(UPLOAD_USER_IMAGE_URI, UserRole.USER.getId());
        ACCESS_MAP.put(LIST_MOVIES_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(MOVIE_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(LIST_MOVIE_BY_GENRE_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(LIST_MOVIE_BY_NAME_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(LOG_IN_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(AUTHORIZATION_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(REGISTRATION_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(SET_LOCAL_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(LIST_HUMAN_URI, UserRole.GUEST.getId());
        ACCESS_MAP.put(HUMAN_URI, UserRole.GUEST.getId());

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (active) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            User user = (User) httpServletRequest.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
            if (user == null) {
                user = new User();
                user.setRoleId(UserRole.GUEST);
            }
            String reqURI = httpServletRequest.getRequestURI();
            Integer accessLevel = ACCESS_MAP.get(reqURI);
            if (accessLevel != null) {
                if (accessLevel <= user.getRoleId().getId()) {
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
