package ltweb.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (JwtTokenUtil.validateToken(token)) {
                    String email = JwtTokenUtil.getEmailFromToken(token);
                    // Thiết lập Authentication
                    // Ví dụ:
                    // UsernamePasswordAuthenticationToken authentication =
                    //         new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    // SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }
}
