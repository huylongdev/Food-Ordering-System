package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import util.Email;

@WebServlet(name = "OtpVerifyServlet", urlPatterns = {"/otp"})
public class OtpVerifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/verifyOtp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        // Validate email format
        if (!isValidEmail(email)) {
            request.setAttribute("message", "Invalid email format.");
            request.getRequestDispatcher("WEB-INF/view/verifyOtp.jsp").forward(request, response);
            return;
        }

        // Generate OTP
        String otp = Email.getRandom(); 

        try {
            // Create an Account object to send the email
            Account ac = new Account();
            ac.setEmail(email);
            ac.setCode(otp); // Store OTP for verification

            // Send OTP
            if (Email.sendEmail(ac)) {
                HttpSession session = request.getSession();
                session.setAttribute("generatedOtp", otp); // Store OTP in session
                request.setAttribute("message", "OTP sent to your email. Please verify.");
            } else {
                request.setAttribute("message", "Failed to send OTP. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this
            request.setAttribute("message", "Failed to send OTP due to an error.");
        }

        request.getRequestDispatcher("WEB-INF/view/verifyOtp.jsp").forward(request, response);
    }

    private boolean isValidEmail(String email) {
        // Basic email validation regex
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }
}
