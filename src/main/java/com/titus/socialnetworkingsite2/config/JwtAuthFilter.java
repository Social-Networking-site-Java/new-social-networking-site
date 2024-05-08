package com.titus.socialnetworkingsite2.config;

import com.titus.socialnetworkingsite2.services.UserDetailImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    //private final UserDetailImpl userDetail;
  //  private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserDetailImpl userDetailImpl;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String userEmail = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailImpl.loadUserByUsername(userEmail);

            System.out.println();
            System.out.println("=========================================");
            System.out.println("User: " + userDetails.getUsername());
            System.out.println("User Password: --> "+ userDetails.getPassword());
            System.out.println("User Authority  --> "+ userDetails.getAuthorities().toString());
            System.out.println("jwt ---> " + jwt);
            System.out.println("userEmail --> " + userEmail);
            System.out.println("===========================================");
            System.out.println();
            System.out.println();

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

//    private String extractJwtRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        return bearerToken.substring(7);
//    }
}


