package com.cos.test.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.test.domain.user.User;
import com.cos.test.domain.user.dto.DeleteReqDto;
import com.cos.test.domain.user.dto.DeleteRespDto;
import com.cos.test.domain.user.dto.JoinReqDto;
import com.cos.test.domain.user.dto.LoginReqDto;
import com.cos.test.service.UserService;
import com.cos.test.util.Script;
import com.google.gson.Gson;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public UserController() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);

	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();
		
		if(cmd.equals("loginForm")) {
			response.sendRedirect("user/login.jsp");
		}else if(cmd.equals("login")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			User userEntity = userService.로그인(dto);
			if(userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity); // 인증주체
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "로그인실패");
			}
		}else if(cmd.equals("joinForm")) {
			response.sendRedirect("user/join.jsp");
		}else if(cmd.equals("join")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			System.out.println("회원가입 : "+dto);
			int result = userService.회원가입(dto);
			if(result == 1) {
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "회원가입 실패");
			}
		}else if(cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			int result = userService.유저네임중복체크(username);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("ok");
			}else {
				out.print("fail");
			}
			out.flush();
		}else if(cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else if (cmd.equals("userList")) { // 글목록보기
			List<User> users = userService.회원목록보기();
			request.setAttribute("users", users);
			RequestDispatcher dis = request.getRequestDispatcher("user/userList.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("delete")) {
			
			// 1. 요청 받은 json 데이터를 자바 오브젝트로 파싱
			BufferedReader br = request.getReader();
			String data = br.readLine(); // DTO 만들어야한다.
			
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);

			// 2. DB에서 id값으로 글 삭제
			int result = userService.회원삭제(dto.getUserId());
			
			// 3. 응답할 json 데이터를 생성
			DeleteRespDto respDto = new DeleteRespDto();
			if(result == 1) {
				respDto.setStatus("ok");
			}else {
				respDto.setStatus("fail");
			}
			String respData = gson.toJson(respDto);
			System.out.println("respData : "+respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
		}
	}

}


