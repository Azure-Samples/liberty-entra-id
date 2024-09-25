package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ibm.websphere.security.social.UserProfileManager;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile/user","/profile/admin"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"users"},
        transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        @SuppressWarnings("unchecked")
        List<String> groups = UserProfileManager.getUserProfile().getIdToken().getClaims().getClaim("groups",
                List.class);
        System.out.println(groups);

        @SuppressWarnings("unchecked")
        List<String> roles = UserProfileManager.getUserProfile().getIdToken().getClaims().getClaim("roles",
                List.class);
        System.out.println(roles);

        String username = request.getUserPrincipal().getName();
        request.setAttribute("name", username);
        request.setAttribute("roles", roles);

        request
                .getRequestDispatcher("/profile.jsp")
                .forward(request, response);
    }
}
