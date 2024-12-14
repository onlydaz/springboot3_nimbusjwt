// script.js
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ email, password }),
                });

                if (response.ok) {
                    const token = await response.text();
                    localStorage.setItem("jwt", token);
                    alert("Login successful!");
                    window.location.href = "/user/profile";
                } else {
                    alert("Login failed. Please check your credentials.");
                }
            } catch (error) {
                console.error("Error during login:", error);
            }
        });
    }
});
