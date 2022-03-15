import com.fastcampus.ch3.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class) // ac를 자동으로 만들어줌
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void TestinsertUser() throws Exception {
        User user = new User("asdf3", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);
    }

    @Test
    public void TestSelectUser() throws Exception {
        deleteAll();
        User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf2");

        assertTrue(user.getId().equals("asdf2"));
    }

    @Test
    public void TestdeleteUser() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt == 0);

        User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);

    }

    @Test
    public void TestspringJdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null); // 괄호안의 조건식이 true면 성공 아니면 실패
    }

    @Test
    public void TestUpdateUser()throws Exception{
        deleteAll();

        deleteAll();
        User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user);

        User user2 = new User("asdf2", "0000", "ccc", "ccc@aaa.com", new Date(), "ig", new Date());
        UpdateUser(user2);
    }

    public void UpdateUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "update user_info set pwd=?,name=?,email=?,birth=?, sns=?, reg_date=sysdate() where id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5, user.getSns());
        pstmt.setString(6, user.getId());
        pstmt.executeUpdate();
    }

    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);

        return pstmt.executeUpdate();
    }

    public User selectUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }
        return null;
    }

    private void deleteAll() throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    public int insertUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "insert into user_info values (?,?,?,?,?,?,now())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate(); // insert, delete, update

        return rowCnt;
    }

}