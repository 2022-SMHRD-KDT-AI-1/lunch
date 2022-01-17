package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDAO1;
import model.MemberDTO;

@WebServlet("/LoginService")
public class LoginService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("로그인되었습니다");
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		System.out.println("email"+email);
		System.out.println("pw"+pw);
		
		//longin메소드가 있는 Dao 호출
		
		MemberDAO1 dao=new MemberDAO1();
		
		//입력받아오 email pw,를 dto로 만들어주기 
		MemberDTO dto=new MemberDTO(email, pw);
		
		//email,pw,tel,address 4개를 받은 dto  생성
		MemberDTO info=dao.login(dto);
		
		//성공실패 다지기
		if(info!=null) {
			System.out.println("로그인 성공");
			response.sendRedirect("main.jsp");
		
			HttpSession session=request.getSession();
			session.setAttribute("info", info);
		}else {
			System.out.println("로그인 실패");
			response.sendRedirect("main.jsp");

		}

	}

}
