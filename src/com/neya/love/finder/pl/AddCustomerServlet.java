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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
		String responseMessage = "";

		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			int age = Integer.parseInt(req.getParameter("age"));
			String country = req.getParameter("country");
			String city = req.getParameter("city");
			int isHidden = Integer.parseInt(req.getParameter("isHidden"));
			if (username != null
					&& !("".equals(username) && password != null && !(""
							.equals(password)))) { 
				
				//validCustomerResponseCustomerUtils.isValidUsername
				CustomerData customer = new CustomerData(username, password,
						email, age, country, city, isHidden);

				CustomerPersistor customerPersistor = new CustomerPersistor();
				if (customerPersistor.addCustomer(customer)) {
					responseMessage = "";
				}
			}

		} catch (NumberFormatException ex) {
			// TODO: return to user that age or isHidden is not valid number
		} catch (Exception e) {

		}
		
		PrintWriter printWriter = resp.getWriter();
		printWriter.println(responseMessage);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
