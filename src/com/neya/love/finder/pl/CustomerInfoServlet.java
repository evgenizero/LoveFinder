package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONSerializer;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.db.CustomerPersistor;

public class CustomerInfoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9182242625477705311L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CustomerData customer = null;

		try {
			HashMap<String, String> jsonObject = new HashMap<String, String>();
			CustomerPersistor customerPersistor = new CustomerPersistor();
			customer = customerPersistor.findById(Integer.valueOf(req
					.getParameter("customerId")));

			resp.setStatus(HttpServletResponse.SC_ACCEPTED);

			ObjectMapper mapper = new ObjectMapper();

			jsonObject.put("user", mapper.writeValueAsString(customer));
			
			PrintWriter printWriter = resp.getWriter();
			printWriter.println(jsonObject);
			printWriter.println();

		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No id provided or bad formatted");
		}
	}

}
