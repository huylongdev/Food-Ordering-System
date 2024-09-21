package controller;

import context.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import util.Email;
import util.PasswordUtil;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("sendOTP".equals(action)) {
            sendOTP(request, response);
        } else if ("verifyOTP".equals(action)) {
            verifyOTP(request, response);
        }
    }

    private void sendOTP(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        AccountDAO acDAO = new AccountDAO();
        String email = request.getParameter("email");

        // Validate email format
        if (!isValidEmail(email)) {
            request.setAttribute("message", "Invalid email format.");
            request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
            return;
        }

        // Generate OTP
        String otp = Email.getRandom();

        // Create account object without saving to DB yet
        Account ac = new Account();
        ac.setEmail(email);
        ac.setCode(otp); // Store OTP for verification

        // Send OTP email
        if (Email.sendEmail(ac)) {
            HttpSession session = request.getSession();
            session.setAttribute("tempAccount", ac); // Store account temporarily
            request.setAttribute("message", "OTP sent to your email. Please verify.");
            request.getRequestDispatcher("WEB-INF/view/verifyOtp.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Failed to send OTP.");
            request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
        }
    }

    private void verifyOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account tempAccount = (Account) session.getAttribute("tempAccount");
        String inputOtp = request.getParameter("otp");

        if (tempAccount != null && tempAccount.getCode().equals(inputOtp)) {
            // OTP is correct, now save the account
            AccountDAO acDAO = new AccountDAO();
            String fullName = request.getParameter("fullname");
            String userName = request.getParameter("user");
            String password = request.getParameter("pass");
            String hashedPassword = PasswordUtil.hashPassword(password);
            String phoneNumber = request.getParameter("phonenumber");
            String address = request.getParameter("address");

            tempAccount.setUserName(userName);
            tempAccount.setPassword(hashedPassword);
            tempAccount.setFullName(fullName);
            tempAccount.setPhoneNumber(phoneNumber);
            tempAccount.setAddress(address);
            tempAccount.setRole(1); // Assuming role is set to 1

            if (acDAO.createAccount(tempAccount)) {
                session.removeAttribute("tempAccount"); // Clear temp account
                session.setAttribute("message", "Registered successfully! Login to continue.");
                response.sendRedirect("login");
            } else {
                request.setAttribute("message", "Failed to register account.");
                request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("WEB-INF/view/verifyOtp.jsp").forward(request, response);
        }
    }

    private boolean isValidEmail(String email) {
        // Basic email validation regex
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    @Override
    public String getServletInfo() {
        return "Register Servlet";
    }
}
