package com.jsp.chap04;

import com.jsp.entity.Dancer;
import com.jsp.repository.DancerMemoryRepo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


// 역할: 댄서 목록 조회 요청을 받아서 데이터베이스에 있는 댄서 정보를
//       가져온 후 적당한 HTML을 찾아서 forwarding
@WebServlet("/chap04/show-list")
public class ShowDancerListServlet extends HttpServlet {

    private DancerMemoryRepo repo = DancerMemoryRepo.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 데이터베이스에 접근하여 댄서 목록을 가져옴
        List<Dancer> dancerList = repo.retrieve();
//        System.out.println("가져와! = " + dancerList);

        // jsp파일에게 보낼 데이터 수송객체에 담기
        req.setAttribute("dancers", dancerList);


        // JSP 파일 열기
        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/chap04/dancer-list.jsp");
        rd.forward(req, resp);


    }
}
