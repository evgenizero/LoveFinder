/**
 * Servlet that receive information 
 * about customer and add it to the db
 * 
 * @author <a href='mailto:yanev93@gmal.com'>Nikolay Yanev</a>
 * @date 14 Dec 2011
 */
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

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.db.CustomerPersistor;

public class AddCustomerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3912995718043333924L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, String> jsonoObject = new HashMap<String, String>();
		int age = 0;
		int isHidden = 1;
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			if (req.getParameter("age") != null)
				age = Integer.parseInt(req.getParameter("age"));

			String country = req.getParameter("country");
			String city = req.getParameter("city");
			if (req.getParameter("isHidden") != null)
				isHidden = Integer.parseInt(req.getParameter("isHidden"));

			if (username != null
					&& !("".equals(username) && password != null && !(""
							.equals(password)))) {

				// validCustomerResponseCustomerUtils.isValidUsername
				CustomerData customer = new CustomerData(username, password,
						email, age, country, city, isHidden);

				CustomerPersistor customerPersistor = new CustomerPersistor();
				if (customerPersistor.addCustomer(customer)) {
					jsonoObject.put("status_code", "1");
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
