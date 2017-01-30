package com.kwan.a4.controller;

import com.kwan.a4.annotation.ControllerTree;
import com.kwan.a4.annotation.HttpMethod;
import com.kwan.a4.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FrontController extends HttpServlet {

    private static final String VIEW_PATH = "/WEB-INF/view/";
    private static final String CONTROLLER_PACKAGE = "com.kwan.a4.controller";
    private static final String DB_FILE = "product.txt";
    private ControllerTree controllerTree;

    @Override
    public void init() throws ServletException {

        ProductService.getInstance().init(DB_FILE);
        controllerTree = new ControllerTree(CONTROLLER_PACKAGE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestURL = request.getServletPath();

        // to return static resources, wrap the request and forward to default
        // The default servlet is declared in web.xml
        if (requestURL.startsWith("/static/")) {
            HttpServletRequest  wrapped = new HttpServletRequestWrapper(request) {
                @Override public String getServletPath() { return ""; }
            };
            getServletContext()
                .getNamedDispatcher("default").forward(wrapped, response);
        }

        Method method = controllerTree.getMethod(requestURL, HttpMethod.GET);
        doProcess(method, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestURL = request.getServletPath();
        Method method = controllerTree.getMethod(requestURL, HttpMethod.POST);
        doProcess(method, request, response);
    }

    /**
     * Process HttpRequest after the servlet finds the element annotated @Mapping
     *
     * @param method   annotated
     * @param request  HttpRequest
     * @param response HttpResponse
     */
    private void doProcess(Method method,
                           HttpServletRequest request,
                           HttpServletResponse response)
        throws ServletException, IOException {
        try {

            if (method == null) {

                request.setAttribute("subject", "Product Maintenance");
                request.setAttribute("body", "start.jsp");
                request.getRequestDispatcher(VIEW_PATH + "index.jsp")
                    .forward(request, response);

            } else {

                String result = (String) method.invoke(method, request, response);
                String[] tokens = result.split(":");

                switch (tokens[0]) {
                    case "forward":
                        request.setAttribute("body", tokens[1]);
                        request.getRequestDispatcher(VIEW_PATH + "baseLayout.jsp")
                            .forward(request, response);
                        break;
                    case "redirect":
                        response.sendRedirect(tokens[1]);
                        break;
                }
            }

        } catch (InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }


}
