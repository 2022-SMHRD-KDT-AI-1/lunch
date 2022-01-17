package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDAO1 {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement psmt = null;
	MemberDTO dto=null;

	int cnt = 0;

	public void DBconn() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbid = "hr";
			String dbpw = "hr";
			conn = DriverManager.getConnection(url, dbid, dbpw);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void DBclose() {

		try {

			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 회원가입 메소드
	public int join(MemberDTO dto) {

		try {
			DBconn();

			String sql = "insert into web_member values(?,?,?,?)";

			// sql-> DB에 전달
			psmt = conn.prepareStatement(sql);

			// ?에 값 넣어주기

			psmt.setString(1, dto.getEmail());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getTel());
			psmt.setString(4, dto.getAddress());

			// 실행
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBclose();

		}
		return cnt;

	}

	public MemberDTO login(MemberDTO dto) {
		MemberDTO info = null;

		System.out.println(dto.getEmail());
		System.out.println(dto.getPw());

		try {
			DBconn();

			String sql = "select*from web_member where email=? and pw=?";

			// SQL문 전달
			psmt = conn.prepareStatement(sql);
			// ?에 값 채우기
			psmt.setString(1, dto.getEmail());
			psmt.setString(2, dto.getPw());

			// 실행
			rs = psmt.executeQuery();

			// rs.next(): 커서를 내렸을때 값이 있는지 없는지 확인하는 메소드
			// 있으면 true 없으면 false
			if (rs.next()) {
				String email = rs.getString("email");
				String pw = rs.getString(2);
				String tel = rs.getString(3);
				String address = rs.getString(4);

				System.out.println(email + pw + tel + address);

				info = new MemberDTO(email, pw, tel, address);
				System.out.println(info.getEmail());

			}

		} catch (Exception e) {

		} finally {
			DBclose();
		}
		return info;
	}

	public int update(MemberDTO info) {
		try {
			DBconn();
			String sql = "update web_member set pw=?,tel=?,address=? where email=?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, info.getPw());
			psmt.setString(2, info.getTel());
			psmt.setString(3, info.getAddress());
			psmt.setString(4, info.getEmail());

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBclose();
		}
		return cnt;
	}

	// 회원정보관리 메소드
	public ArrayList<MemberDTO> showMember() {
		ArrayList<MemberDTO> list =new ArrayList<MemberDTO>();
		
		try {
			DBconn();
			String sql = "select email,tel,address from web_member";

			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			// 로그인과 다르게 전체 회원정보를 가지고 오기 때문에 반복문 사용
			// rs.next가 true일 때 즉, 가져올 값이 있을 때까지 반복을 돌면서 가지고 온다.
			while (rs.next()) {
				String email = rs.getString(1);
				String tel = rs.getString(2);
				String address = rs.getString(3);
				
				
				 dto=new MemberDTO(email,tel,address);
				 list.add(dto);
				 
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return list;
		}
	}

}
