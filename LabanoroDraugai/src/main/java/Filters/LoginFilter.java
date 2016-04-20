package Filters;

import BusinessLogic.EJB.LoginBean;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Povilas <your.name at your.org>
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        LoginBean session = (LoginBean) req.getSession().getAttribute("loginBean");
        String url = req.getRequestURI();
        
        if(session== null){
            int i = 1;
        }
        
        if (url.indexOf("logout") >= 0) {
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.invalidateSession();
            //ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
            req.getSession().removeAttribute("loginBean");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login.xhtml");
        }
        else{
            String test = req.getServletContext().getContextPath();
            //resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
            chain.doFilter(request, response);
        }
//        if (session == null) {
//            if (url.indexOf("manohome.xhtml") >= 0 || url.indexOf("logout.xhtml") >= 0) {
//                resp.sendRedirect(req.getServletContext().getContextPath() + "/login.xhtml");
//            } else {
//                chain.doFilter(request, response);
//            }
//        } else if (url.indexOf("login.xhtml") >= 0) {
//            resp.sendRedirect(req.getServletContext().getContextPath() + "/manohome.xhtml");
//        } else if (url.indexOf("logout.xhtml") >= 0) {
//            req.getSession().removeAttribute("loginBean");
//            resp.sendRedirect(req.getServletContext().getContextPath() + "/login.xhtml");
//        } else {
//            chain.doFilter(request, response);
//        }
    }

    @Override
    public void destroy() {

    }

}
