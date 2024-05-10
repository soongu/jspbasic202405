package com.jsp.chap04;

import com.jsp.entity.Dancer;
import com.jsp.repository.DancerJdbcRepo;
import com.jsp.repository.DancerMemoryRepo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 역할: 새로운 댄서 정보를 데이터베이스에 등록하기 위해
//       댄서 정보들을 가져와서 처리하는 역할
@WebServlet("/chap04/new-dancer")
public class AddNewDancerServlet extends HttpServlet {

//    private DancerMemoryRepo repo = DancerMemoryRepo.getInstance();
    private DancerJdbcRepo repo = DancerJdbcRepo.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 요청파라미터를 읽어서 댄서 정보 가져오기
        req.setCharacterEncoding("utf-8");

        String name = req.getParameter("name");
        String crewName = req.getParameter("crewName");
        String danceLevel = req.getParameter("danceLevel");
        String[] genres = req.getParameterValues("genres");

        // 댄서 객체 생성
        Dancer dancer = new Dancer();
        dancer.setName(name);
        dancer.setCrewName(crewName);
        dancer.setDanceLevel(Dancer.DanceLevel.valueOf(danceLevel));

        List<Dancer.Genre> genreList = new ArrayList<>();
        for (String genre : genres) {
            genreList.add(Dancer.Genre.valueOf(genre));
        }
        dancer.setGenres(genreList);

        System.out.println("dancer = " + dancer);

        // 생성된 댄서 객체를 데이터베이스에 저장
        // 데이터베이스 처리에 특화된 객체에게 위임
        repo.save(dancer);

        // JSP에게 전달할 동적데이터를 어떻게 전달할 것인가?
        // 수송객체 (page, request, session, application)
        // request: 한번의 요청과 응답이 끝날동안만 보관
        // session: 브라우저가 꺼질때까지 or 세션시간이 만료될때까지 보관
//        req.setAttribute("name", name);
//        req.setAttribute("crew", crewName);
//        req.setAttribute("level", danceLevel);

        req.setAttribute("d", dancer);

        // 적당한 HTML(JSP) 응답
        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/chap04/result.jsp");
        rd.forward(req, resp);

    }
}
