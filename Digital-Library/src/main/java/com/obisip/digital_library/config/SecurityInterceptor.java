package com.obisip.digital_library.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        HttpSession session = request.getSession(false);

        Long userId = null;
        String userRole = null;

        if (session != null) {
            userId = (Long) session.getAttribute("userId");
            userRole = (String) session.getAttribute("userRole");
        }

        /*
         * Public endpoints
         */
        if (uri.equals("/")
                || uri.equals("/login")
                || uri.equals("/register")
                || uri.equals("/books")
                || uri.equals("/api/auth/login")
                || uri.equals("/api/users/register")
                || uri.startsWith("/css/")
                || uri.startsWith("/js/")) {

            return true;
        }

        /*
         * Current-user information
         */
        if (uri.equals("/api/auth/me")) {

            if (userId == null) {
                sendUnauthorized(response, "Please login first.");
                return false;
            }

            return true;
        }

        /*
         * Logout
         */
        if (uri.equals("/api/auth/logout")) {
            return true;
        }

        /*
         * Books API
         *
         * Everyone can view books.
         * Only ADMIN can add, edit or delete books.
         */
        if (uri.equals("/api/books")
                || uri.matches("/api/books/\\d+")) {

            if ("GET".equalsIgnoreCase(method)) {
                return true;
            }

            if (!isAdmin(userRole)) {
                sendForbidden(response, "Admin access required.");
                return false;
            }

            return true;
        }

        /*
         * User/member management
         *
         * Only ADMIN can view or delete members.
         */
        if (uri.equals("/api/users")
                || uri.matches("/api/users/\\d+")) {

            if (!isAdmin(userRole)) {
                sendForbidden(response, "Admin access required.");
                return false;
            }

            return true;
        }

        /*
         * Borrowing
         *
         * Logged-in users can issue and return books.
         */
        if (uri.startsWith("/api/borrow/")) {

            if (userId == null) {
                sendUnauthorized(response, "Please login first.");
                return false;
            }

            /*
             * Only ADMIN can view all borrow records
             * or mark fines as paid.
             */
            if (uri.equals("/api/borrow")
                    || uri.matches("/api/borrow/\\d+")
                    || uri.startsWith("/api/borrow/fine-paid/")) {

                if (!isAdmin(userRole)) {
                    sendForbidden(response, "Admin access required.");
                    return false;
                }
            }

            return true;
        }

        /*
         * User borrow history
         */
        if (uri.matches("/api/borrow/user/\\d+")) {

            if (userId == null) {
                sendUnauthorized(response, "Please login first.");
                return false;
            }

            return true;
        }

        /*
         * Advance bookings
         */
        if (uri.startsWith("/api/bookings/")) {

            if (userId == null) {
                sendUnauthorized(response, "Please login first.");
                return false;
            }

            return true;
        }

        /*
         * Contact queries
         *
         * Logged-in users can submit queries.
         * Only ADMIN can view queries.
         */
        if (uri.equals("/api/contact")) {

            if (userId == null) {
                sendUnauthorized(response, "Please login first.");
                return false;
            }

            if ("GET".equalsIgnoreCase(method)
                    && !isAdmin(userRole)) {

                sendForbidden(response, "Admin access required.");
                return false;
            }

            return true;
        }

        /*
         * Admin web pages
         */
        if (uri.equals("/admin")
                || uri.startsWith("/admin/")) {

            if (!isAdmin(userRole)) {
                response.sendRedirect("/login");
                return false;
            }

            return true;
        }

        /*
         * Dashboard requires login.
         */
        if (uri.equals("/dashboard")
                || uri.equals("/contact")) {

            if (userId == null) {
                response.sendRedirect("/login");
                return false;
            }

            return true;
        }

        return true;
    }


    private boolean isAdmin(String role) {

        return role != null
                && role.equalsIgnoreCase("ADMIN");
    }


    private void sendUnauthorized(
            HttpServletResponse response,
            String message) throws Exception {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }


    private void sendForbidden(
            HttpServletResponse response,
            String message) throws Exception {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }
}