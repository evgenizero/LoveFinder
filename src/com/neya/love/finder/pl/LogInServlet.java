package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import com.neya.love.finder.db.CustomerPersistor;

public class LogInServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5699243519242133426L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, String> jsonoObject = new HashMap<String, String>();
		
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			if (username != null
					&& !("".equals(username) && password != null && !(""
							.equals(password)))) {

				CustomerPersistor customerPersistor = new CustomerPersistor();
				int customerId = customerPersistor.logIn(username, password);
				
				if (customerId > 0) {
					jsonoObject.put("status_code", "1");
					jsonoObject.put("customer_id", String.valueOf(customerId));
					jsonoObject.put("status-message",
							"User was added successfully");
				}
			} else {
				System.out.println("Error");
				jsonoObject.put("status_code", "0");
				jsonoObject.put("status-message",
						"Username or password are not correct!");
			}

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter printWriter = resp.getWriter();
		printWriter.println(toJSON(jsonoObject));
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private JSON toJSON(Map<String, String> jsonMessage) {
		return JSONSerializer.toJSON(jsonMessage);
	}
}
