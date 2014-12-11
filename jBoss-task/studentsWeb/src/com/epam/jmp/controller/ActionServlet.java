package com.epam.jmp.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jmp.entity.Group;
import com.epam.jmp.service.GroupManager;
import com.epam.jmp.service.StudentManager;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private StudentManager studentManagerBean;

	@EJB
	private GroupManager groupManagerBean;

	public StudentManager getStudentManagerBean() {
		return studentManagerBean;
	}

	public void setStudentManagerBean(StudentManager studentManagerBean) {
		this.studentManagerBean = studentManagerBean;
	}

	public GroupManager getGroupManagerBean() {
		return groupManagerBean;
	}

	public void setGroupManagerBean(GroupManager groupManagerBean) {
		this.groupManagerBean = groupManagerBean;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		String command = request.getParameter("command");
		switch (command) {
		case "getAllGroups":
			request.setAttribute("groups", groupManagerBean.getGroupList());
			page = "/pages/groups.jsp";
			break;

		case "getStudentsByGroup":
			Long groupId = Long.valueOf(request.getParameter("groupId"));
			Group group = groupManagerBean.getGroupById(groupId);
			request.setAttribute("group", group);
			request.setAttribute("students",
					studentManagerBean.getStudentsByGroup(group));
			page = "/pages/students.jsp";
			break;

		case "getAllStudents":
			request.setAttribute("students",
					studentManagerBean.getStudentList());
			page = "/pages/students.jsp";
			break;

		case "getStudentById":
			Long studentId = Long.valueOf(request.getParameter("studentId"));
			request.setAttribute("student",
					studentManagerBean.getStudentById(studentId));
			page = "/pages/student.jsp";
			break;
		default:
			break;
		}
		if (page != null) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
	}
}