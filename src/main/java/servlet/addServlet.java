package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.student;
import dao.studentdao;

/**
 * Servlet implementation class addServlet
 */
public class addServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		student s = new student();
		s.setUsername(username);
		s.setUserpwd(userpwd);
		
		studentdao dao = new studentdao();
		int flag = dao.addstudent(s);
		PrintWriter out = response.getWriter();
		if(flag!=0){
			out.print("<script language=javascript>alert('添加成功！');window.location.href='find.jsp';</script>");
		}
		else{
			out.print("<script language=javascript>alert('添加失败！');window.location.href='add.jsp';</script>");
		}

		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private int Integer(String parameter) {
		// TODO Auto-generated method stub
		return 0;
	}

}
