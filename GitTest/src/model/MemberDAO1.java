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

	// ȸ������ �޼ҵ�
	public int join(MemberDTO dto) {

		try {
			DBconn();

			String sql = "insert into web_member values(?,?,?,?)";

			// sql-> DB�� ����
			psmt = conn.prepareStatement(sql);

			// ?�� �� �־��ֱ�

			psmt.setString(1, dto.getEmail());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getTel());
			psmt.setString(4, dto.getAddress());

			// ����
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

			// SQL�� ����
			psmt = conn.prepareStatement(sql);
			// ?�� �� ä���
			psmt.setString(1, dto.getEmail());
			psmt.setString(2, dto.getPw());

			// ����
			rs = psmt.executeQuery();

			// rs.next(): Ŀ���� �������� ���� �ִ��� ������ Ȯ���ϴ� �޼ҵ�
			// ������ true ������ false
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

	// ȸ���������� �޼ҵ�
	public ArrayList<MemberDTO> showMember() {
		ArrayList<MemberDTO> list =new ArrayList<MemberDTO>();
		
		try {
			DBconn();
			String sql = "select email,tel,address from web_member";

			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			// �α��ΰ� �ٸ��� ��ü ȸ�������� ������ ���� ������ �ݺ��� ���
			// rs.next�� true�� �� ��, ������ ���� ���� ������ �ݺ��� ���鼭 ������ �´�.
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
