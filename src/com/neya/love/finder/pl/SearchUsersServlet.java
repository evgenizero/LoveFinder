package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.db.CustomerPersistor;
import com.neya.love.finder.services.CustomerService;

public class SearchUsersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6853992575970685736L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			HashMap<String, String> jsonObject = new HashMap<String, String>();

			ObjectMapper mapper = new ObjectMapper();
			CustomerData customer = mapper.readValue(req.getParameter("user"),
					CustomerData.class);

			CustomerPersistor customerPersistor = new CustomerPersistor();
			List<CustomerData> customers = null;
			customers = customerPersistor.getUsers(customer);

			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			
			jsonObject.put("users", mapper.writeValueAsString(customers));
			PrintWriter writer = resp.getWriter();
			writer.println(jsonObject);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Error parsing user data");
		}
	}
}
