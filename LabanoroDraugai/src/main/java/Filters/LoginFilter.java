package Filters;

import BusinessLogic.EJB.LoginBean;
import java.io.IOException;
import java.util.Enumeration;
import javax.faces.application.ResourceHandler;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Povilas <your.name at your.org>
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String reqURI = request.getRequestURI();
        String loginURL = request.getContextPath() + "/login.xhtml";
        String stripePaymentURI = request.getContextPath() + "/stripePayment.xhtml";
        String pageNotFoundURL = request.getContextPath() + "/pageNotFound.xhtml";
        String pageNotAccesibleURL = request.getContextPath() + "/pageNotAccesible.xhtml";
        String logoutURI = request.getContextPath() + "/logout.xhtml";
        String addSummerhouseURI = request.getContextPath() + "/addSummerhouse.xhtml";
        String editSummerhouseURI = request.getContextPath() + "/editSummerhouse.xhtml";
        String removeSummerhouseURI = request.getContextPath() + "/deleteSummerhouse.xhtml";
        String deleteMemberURI = request.getContextPath() + "/deleteMember.xhtml";
        String meritURI = request.getContextPath() + "/merit.xhtml";
        String editRegistrationFormURI = request.getContextPath() + "/editRegistrationForm.xhtml";
        String editSettingsURI = request.getContextPath() + "/settings.xhtml";
        String recommendationURI = request.getContextPath() + "/recommendation.xhtml";
        String myProfileURI = request.getContextPath() + "/myProfile.xhtml";
        String editProfileURI = request.getContextPath() + "/editProfile.xhtml";
        String payMembershipFeeURI = request.getContextPath() + "/payMembershipFee.xhtml";
        String registrationURI = request.getContextPath() + "/registration.xhtml";
        String memberReviewURI = request.getContextPath() + "/membersReview.xhtml";
        String mySummerhousesURI = request.getContextPath() + "/mySummerhouses.xhtml";
        String pointsURI = request.getContextPath() + "/points.xhtml";
        String registrationConfirmURI = request.getContextPath() + "/registrationconfirm.xhtml";
        String reservationURI = request.getContextPath() + "/reservation.xhtml";
        String summerhouseURI = request.getContextPath() + "/summerhouse.xhtml";
        String summerhouseMoreDetailsURI = request.getContextPath() + "/summerhouseMoreDetails.xhtml";
        String summerhouseEditURI = request.getContextPath() + "/summerhouseEdit.xhtml";
        String homeURL = request.getContextPath() + "/home.xhtml";
        String confirmPaymentsURI = request.getContextPath() + "/confirmPayments.xhtml";
        String myServicesURI = request.getContextPath() + "/myServices.xhtml";
        String servicesURI = request.getContextPath() + "/services.xhtml";
        String addMessageURI = request.getContextPath() + "/addMessage.xhtml";
        String removeMessageURI = request.getContextPath() + "/removeMessage.xhtml";
        String addPaymentsURI = request.getContextPath() + "/addPayments.xhtml";
        String addServiceURI = request.getContextPath() + "/addService.xhtml";
        String editServiceURI = request.getContextPath() + "/editService.xhtml";
        String deleteServiceURI = request.getContextPath() + "/deleteService.xhtml";
        int indexOfPay = request.getRequestURI().indexOf("payMembershipFee");
        int indexOfRes = request.getRequestURI().indexOf("resources");

        boolean loggedIn = (session != null);//&& (session.getAttribute("account") != null);
        boolean loginRequest = request.getRequestURI().equals(loginURL);
        boolean homeRequest = request.getRequestURI().equals(homeURL);
        boolean logoutRequest = request.getRequestURI().equals(logoutURI);
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(request.getHeader("Faces-Request"));
        boolean addSummerhouseRequest = request.getRequestURI().equals(addSummerhouseURI);
        boolean editSummerhouseRequest = request.getRequestURI().equals(editSummerhouseURI);
        boolean removeSummerhouseRequest = request.getRequestURI().equals(removeSummerhouseURI);
        boolean deleteMemberRequest = request.getRequestURI().equals(deleteMemberURI);
        boolean meritRequest = request.getRequestURI().equals(meritURI);
        boolean editRegistrationFormRequest = request.getRequestURI().equals(editRegistrationFormURI);
        boolean editSettingsRequest = request.getRequestURI().equals(editSettingsURI);
        boolean recommendationRequest = request.getRequestURI().equals(recommendationURI);
        boolean myProfileRequest = request.getRequestURI().equals(myProfileURI);
        boolean editProfileRequest = request.getRequestURI().equals(editProfileURI);
        boolean payMembershipFeeRequest = request.getRequestURI().equals(payMembershipFeeURI);
        boolean memberReviewRequest = request.getRequestURI().equals(memberReviewURI);
        boolean mySummerhousesRequest = request.getRequestURI().equals(mySummerhousesURI);
        boolean pointsRequest = request.getRequestURI().equals(pointsURI);
        boolean registrationRequest = request.getRequestURI().equals(registrationURI);
        boolean registrationConfirmRequest = request.getRequestURI().equals(registrationConfirmURI);
        boolean reservationRequest = request.getRequestURI().equals(reservationURI);
        boolean summerhouseRequest = request.getRequestURI().equals(summerhouseURI);
        boolean summerhouseMoreDetailsRequest = request.getRequestURI().equals(summerhouseMoreDetailsURI);
        boolean pageNotFoundRequest = request.getRequestURI().equals(pageNotFoundURL);
        boolean pageNotAccesibleRequest = request.getRequestURI().equals(pageNotAccesibleURL);
        boolean stripePaymentRequest = request.getRequestURI().equals(stripePaymentURI);
        boolean summerhouseEditRequest = request.getRequestURI().equals(summerhouseEditURI);
        boolean confirmPaymentsRequest = request.getRequestURI().equals(confirmPaymentsURI);
        boolean myServicesRequest = request.getRequestURI().equals(myServicesURI);
        boolean servicesRequest = request.getRequestURI().equals(servicesURI);
        boolean addMessageRequest = request.getRequestURI().equals(addMessageURI);
        boolean removeMessageRequest = request.getRequestURI().equals(removeMessageURI);
        boolean addPaymentsRequest = request.getRequestURI().equals(addPaymentsURI);
        boolean addServiceRequest = request.getRequestURI().equals(addServiceURI);
        boolean editServiceRequest = request.getRequestURI().equals(editServiceURI);
        boolean deleteServiceRequest = request.getRequestURI().equals(deleteServiceURI);
        boolean admin = false;
        boolean candidate = false;
        boolean member = false;
        boolean button = true;
        boolean account = false;

        if (session != null && session.getAttribute("status") != null) {
            admin = session.getAttribute("status").toString().equals("Administratorius");
            candidate = session.getAttribute("status").toString().equals("Kandidatas");
            member = session.getAttribute("status").toString().equals("Narys");
            //button = (boolean) session.getAttribute("mygtukas");
//            active = session.getAttribute("status").toString().equals("Aktyvus");
//            notActive = session.getAttribute("status").toString().equals("Neaktyvus");

        }

//        if (session != null && session.getAttribute("mygtukas") != null) {
//            button = (boolean) session.getAttribute("mygtukas");
//        }
        if ((loggedIn || loginRequest || resourceRequest) && !logoutRequest && !addSummerhouseRequest && !editSummerhouseRequest
                && !removeSummerhouseRequest && !deleteMemberRequest && !meritRequest && !editRegistrationFormRequest
                && !editSettingsRequest && !memberReviewRequest && !mySummerhousesRequest && !payMembershipFeeRequest && !pointsRequest
                && !reservationRequest && !summerhouseRequest && !summerhouseMoreDetailsRequest && indexOfPay == -1 && !stripePaymentRequest
                && !summerhouseEditRequest) {
            if (!resourceRequest) { // Prevent browser from caching restricted resources. See also http://stackoverflow.com/q/4194207/157882
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.1
                response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                response.setDateHeader("Expires", 0); // Proxies.
            }

            if ((registrationRequest || registrationConfirmRequest) && (admin || candidate || member)) {
                //response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.sendRedirect(pageNotAccesibleURL);
            } else if ((myProfileRequest || editProfileRequest || recommendationRequest || homeRequest) && (admin || candidate || member)) {
                chain.doFilter(request, response);
            } else if (indexOfRes != -1 || pageNotFoundRequest || pageNotAccesibleRequest || loginRequest || resourceRequest) {
                chain.doFilter(request, response);
            } else if (registrationRequest) {
                chain.doFilter(request, response);
            } else if ((confirmPaymentsRequest || removeMessageRequest || addPaymentsRequest || addServiceRequest || editServiceRequest || deleteServiceRequest) && admin) {
                chain.doFilter(request, response);
            } else if ((myServicesRequest || servicesRequest || addMessageRequest) && (admin || member)) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(pageNotFoundURL);
            }
            //chain.doFilter(request, response); // So, just continue request.
        } else if (loggedIn && logoutRequest) {
            request.getSession().removeAttribute("loginBean");
            response.sendRedirect(loginURL);
        } else if (loggedIn) {
            if (addSummerhouseRequest || editSummerhouseRequest
                    || removeSummerhouseRequest || deleteMemberRequest || meritRequest || editRegistrationFormRequest
                    || editSettingsRequest || summerhouseEditRequest) {
                if (admin) {
                    chain.doFilter(request, response);
                } else {
                    response.sendRedirect(pageNotAccesibleURL);
                }
            } else if (payMembershipFeeRequest && (admin || member) && button) {
                chain.doFilter(request, response);
            } else if ((memberReviewRequest || mySummerhousesRequest || pointsRequest
                    || reservationRequest || summerhouseRequest || summerhouseMoreDetailsRequest || stripePaymentRequest) && (admin || member)) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(pageNotFoundURL);

            }
        } else if (ajaxRequest) {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().printf(AJAX_REDIRECT_XML, loginURL); // So, return special XML response instructing JSF ajax to send a redirect.
        } else {
            response.sendRedirect(loginURL); // So, just perform standard synchronous redirect.
        }
    }

    @Override
    public void destroy() {

    }

}
