package com.jsp.chap01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

// 역할: HTTP 통신의 요청과 응답 데이터를 손쉽게 처리할
// 수 있게 도와주는 클래스

// WAS (톰캣) 에게 이 서블릿을 언제 호출할지 URL을 매핑
@WebServlet("/login")
public class BasicServlet extends HttpServlet {

    public BasicServlet() {
        System.out.println("\n\n\nBasic Servlet 객체가 생성됨!\n\n\n");
    }

    // 서버는 클라이언트에서 요청이 들어오면
    // HTTP 메세지를 분석하여 요청 내용을 파악해야 함
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 메시지 정보를 쉽게 읽도록 많은 메서드를 제공

        // 요청 방식 (조회, 생성, 수정, 삭제)
        String method = req.getMethod();

        // 요청 URI
        String requestURI = req.getRequestURI();

        // 요청 파라미터 정보
        String queryString = req.getQueryString();

        // 요청 헤더 정보 읽기
        String header = req.getHeader("Cache-Control");

        System.out.println("method = " + method);
        System.out.println("requestURI = " + requestURI);
        System.out.println("queryString = " + queryString);
        System.out.println("header = " + header);

        // 쿼리스트링 (요청 파라미터) 하나씩 읽기
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String grade = req.getParameter("grade");

        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("grade = " + grade);

        // 서버의 응답 처리
        // 비즈니스 로직 : 나이를 기반으로 출생년도를 계산
        //   학점이 F면 과락처리, 아니면 통과처리
        int birthYear = 0;
        try {
            birthYear = LocalDate.now().getYear() - Integer.parseInt(age) + 1;
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            return;
        }

        String message;
        switch (grade) {
            case "F":
                message = "재수강 하셔야 합니다.";
                break;
            default:
                message = "시험에 통과하셨습니다.";
        }

        // 응답 메시지 생성
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        // 응답 바디에 넣을 html 생성
        PrintWriter w = resp.getWriter();

        w.write("<!DOCTYPE html>\n");
        w.write("<html>\n");
        w.write("<head>\n");
        w.write("</head>\n");
        w.write("<body>\n");
        w.write("   \t<h1>\n");
        w.write(String.format("%s님은 %d년생입니다.", name, birthYear));
        w.write("   </h1>\n");
        w.write("<h2>" + message + "</h2>");
        w.write("</body>\n");
        w.write("</html>");

    }
}
