package com.kwan.a4.controller;

import com.kwan.a4.annotation.HttpMethod;
import com.kwan.a4.annotation.Mapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Mapping("/login")
public class LoginController {

    private LoginController() {
    }

    @Mapping(value = "/", method = HttpMethod.GET)
    public static String login(HttpServletRequest request,
                               HttpServletResponse response) {

        request.setAttribute("subject", "Login");

        return "forward:loginForm.jsp";
    }

    @Mapping(value = "/failed", method = HttpMethod.POST)
    // j_security_check, request post
    public static String loginFailed(HttpServletRequest request,
                                     HttpServletResponse response) {

        request.setAttribute("subject", "Login");
        request.getSession().setAttribute("footer",
            "[Login failed] Invalid Username or Password");

        return "forward:loginForm.jsp";
    }

    @Mapping(value = "/out", method = HttpMethod.GET)
    public static String logout(HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException, IOException {
        request.logout();

        String previousPath = request.getParameter("previousPath");

        return "redirect:" + (previousPath == null ? "/" : previousPath);
    }
}
