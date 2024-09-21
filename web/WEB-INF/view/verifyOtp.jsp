<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Verify OTP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
    <div class="container">
        <h2>Verify OTP</h2>
        <form action="register" method="post">
            <div class="mb-3">
                <label for="otp" class="form-label">Enter OTP sent to your email</label>
                <input type="text" id="otp" name="otp" class="form-control" required />
            </div>
            <input type="hidden" name="action" value="verifyOTP" />
            <button type="submit" class="btn btn-primary">Verify OTP</button>
        </form>
        <p class="text-danger">${message}</p>
    </div>
</body>
</html>
