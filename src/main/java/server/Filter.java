package server;

import hyggemvc.controller.Controller;
import hyggemvc.router.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String path = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length());

        if (path.startsWith("/assets")) {
            chain.doFilter(request, response); // Goes to default servlet for assets
        } else {
            forwardToController((HttpServletRequest) request, (HttpServletResponse) response, path);
        }
    }

    private void forwardToController(HttpServletRequest request, HttpServletResponse response, String path) {
        Router router = new BasicRouter();
        Route route = new BasicRoute("controller");
        router.inflateRoute(route, path);
        RouteCaller routeCaller = new RouteCaller(route, request, response);
        Controller controller = routeCaller.callRoute();
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
