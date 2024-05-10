package com.jsp.chap04;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 역할: 댄서 등록 화면을 요청하면 해당 html파일을 열기만해주는 역할
@WebServlet("/chap04/dancer/form")
public class DancerFormRequestServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 적당한 view에게 화면처리를 위임
        // forwarding: 화면 파일을 찾아서 열어주는 개념
        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/chap04/register.jsp");
        rd.forward(req, resp);

    }
}
