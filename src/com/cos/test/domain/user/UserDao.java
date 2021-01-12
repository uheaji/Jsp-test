package com.cos.test.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.test.config.DB;
import com.cos.test.domain.user.dto.JoinReqDto;
import com.cos.test.domain.user.dto.LoginReqDto;

public class UserDao {
	
	public int deleteById(int id) { // 회원정보삭제
		String sql = "DELETE FROM user WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	public static List<User> findAll(){
		String sql = "SELECT * FROM  user ORDER BY id ASC";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		List<User> users = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			
			while(rs.next()) { // 커서를 이동하는 함수
				User user = User.builder() 
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.build();
				users.add(user);	
			}
            // while문 안에서 return하면 한 행 밖에 못 읽는다.
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		
		return null;
	}

	
	public User findByUsernameAndPassword(LoginReqDto dto) { // 로그인
		String sql = "SELECT id, username, email, userRole FROM user WHERE username = ? AND password = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null; 
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs =  pstmt.executeQuery();
			
			// Persistence API : 영속성 API
			if(rs.next()) {
                // user 오브젝트로 리턴해주기
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.build();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		
		return null;
	}

	
	public int findByUsername(String username) { // 유저네임 중복체크
		String sql = "SELECT * FROM user WHERE username = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs =  pstmt.executeQuery();
			if(rs.next()) {
				return 1; // 있어
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs); // rs도 닫아야하니까 DB.java에 close()함수 오버로딩하기!
		}
		return -1; // 없어
	}

	
	public int save(JoinReqDto dto) { // 회원가입
		String sql = "INSERT INTO user(username, password, email, userRole) VALUES(?,?,?, 'USER')";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt  = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

}
