package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    // DB에 붙으려면 Datasource 필요
    private final DataSource dataSource;

    // 스프링한테 주입받아야 함
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        // 쿼리의 결과를 받음
        ResultSet rs = null;

        try {
            conn = getConnection();
            // RETURN_GENERATED_KEYS : DB에 INSERT를 할 때 ID 값 자동 생성
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 쿼리의 첫 번째 물음표에 멤버의 이름을 넣음
            pstmt.setString(1, member.getName());

            // pstmt 쿼리 날라감
            pstmt.executeUpdate();
            // RETURN_GENERATED_KEYS 로 인해 값이 생성되면서 생긴 ID 값을 가져옴
            rs = pstmt.getGeneratedKeys();

            // rs.next() : rs의 값이 있는지 확인
            if(rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
            // 쿼리 관련해서 예외가 많이 발생하기 때문에 try - catch ㅈ라 걸어줘야 함
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 사용이 끝나면 리소스를 끊어줘야함
            // 끊어주지 않으면 Connection 쌓이다가 터짐..
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {

        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {

        String sql = "select * from member where name=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {

        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    private Connection getConnection() {
        // Spring Framework을 통해서 DataSource를 쓸 때는 DataSourceUtils을 통해서 Connection 을 획득 해야함
        // 그래야 트랜잭션이 걸릴 수 있음, Database Connection 을 똑같이 유지시켜줌
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        // 닫을때도 DataSourceUtils를 통해 닫아야 함
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
