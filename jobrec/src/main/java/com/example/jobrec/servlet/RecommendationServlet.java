package com.example.jobrec.servlet;

import com.example.jobrec.entity.Item;
import com.example.jobrec.entity.ResultResponse;
import com.example.jobrec.recommendation.Recommendation;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecommendationServlet", urlPatterns = {"/recommendation"})
public class RecommendationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }
        //get user ID
        String user_id = request.getParameter("user_id");
        //get location
        String location = request.getParameter("location");
        Recommendation recommendation = new Recommendation();
        List<Item> items = recommendation.recommendItems(user_id, location);
        response.setContentType("application/json");
        response.getWriter().write("This is Recommendation Servlet");
    }
}
