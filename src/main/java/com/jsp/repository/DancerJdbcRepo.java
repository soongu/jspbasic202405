package com.jsp.repository;

import com.jsp.chap05.Person;
import com.jsp.entity.Dancer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE tbl_dancer (
	id INT(8) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    crew_name VARCHAR(255),
    dance_level VARCHAR(50)
);

 */

// 역할: 실제 데이터베이스에 댄서들을 CRUD
// Model
public class DancerJdbcRepo implements DancerRepository {

    private static DancerJdbcRepo repo = new DancerJdbcRepo();

    // 싱글톤 구현
    private DancerJdbcRepo() {}

    // 싱글객체를 리턴하는 메서드
    public static DancerJdbcRepo getInstance() {
        return repo;
    }

    private String username = "root"; // db계정명
    private String password = "mariadb"; // db 패스워드
    private String url = "jdbc:mariadb://localhost:3306/spring5"; // db url : 데이터베이스 설치 위치
    private String driverClassName = "org.mariadb.jdbc.Driver"; // db벤더별 전용 커넥터 클래스

    // 댄서를 데이터베이스에 저장하는 기능
    public boolean save(Dancer dancer) {
        try (Connection conn
                     = DriverManager.getConnection(url, username, password)) {
            Class.forName(driverClassName);

            String sql = "INSERT INTO tbl_dancer " +
                    "(name, crew_name, dance_level) " +
                    "VALUES (?, ?, ?)";

            // 4. SQL 실행 객체 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 5. ? 값 채우기
            pstmt.setString(1, dancer.getName());
            pstmt.setString(2, dancer.getCrewName());
            pstmt.setString(3, dancer.getDanceLevel().toString());

            // 6. 실행 명령
            // INSERT, UPDATE, DELETE 같은 명령을 사용
            pstmt.executeUpdate();
            return true;

            // 7. 데이터베이스 연결 해제

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 댄서리스트를 반환하는 기능
    public List<Dancer> retrieve() {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            Class.forName(driverClassName);

            String sql = "SELECT * FROM tbl_dancer";

            // SQL 실행 객체 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // ? 채우기

            // 실행 명령 - SELECT는 다른 메서드를 사용
            // ResultSet : SELECT의 결과집합 표를 가져옴
            ResultSet rs = pstmt.executeQuery();

            // ResultSet 데이터 가져오기
            List<Dancer> dancerList = new ArrayList<>();
            while (rs.next()) {
                // 커서가 가리키는 행의 데이터를 하나씩 추출
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String crewName = rs.getString("crew_name");
                String danceLevel = rs.getString("dance_level");

                Dancer dancer = new Dancer();
                dancer.setId(id);
                dancer.setName(name);
                dancer.setCrewName(crewName);
                dancer.setDanceLevel(Dancer.DanceLevel.valueOf(danceLevel));

                dancerList.add(dancer);
            }
            return dancerList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void delete(String id) {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            Class.forName(driverClassName);

            String sql = "DELETE FROM tbl_dancer WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            pstmt.executeUpdate();


        } catch (Exception e) {

        }
    }
}
