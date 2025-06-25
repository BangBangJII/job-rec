package com.example.jobrec.servlet;

import com.example.jobrec.db.MySQLConnection;
import com.example.jobrec.entity.HistoryRequestBody;
import com.example.jobrec.entity.ResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "HistoryServlet", urlPatterns = {"/history"})
public class HistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        //protect servlet
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }
        //transfer the request to class
        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);

        MySQLConnection connection = new MySQLConnection();
        //use the setFavoriteItem() that just implemented in MySQLConnection
        connection.setFavoriteItems(body.userId, body.favourite);
        connection.close();
        //check Redis


        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        //protect servlets
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }

        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);
        response.setContentType("application/json");
        MySQLConnection connection = new MySQLConnection();
        connection.removeFavoriteItems(body.userId, body.favourite.getId());
        connection.close();
        //check Redis


        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
    }
}
