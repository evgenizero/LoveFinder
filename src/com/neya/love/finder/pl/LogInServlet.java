package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import com.neya.love.finder.db.CustomerPersistor;

public class LogInServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5699243519242133426L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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
					jsonoObject.put("customer_id", String.valueOf(customerId));
					resp.setStatus(HttpServletResponse.SC_ACCEPTED);
					PrintWriter printWriter = resp.getWriter();
					printWriter.println(toJSON(jsonoObject));
				} else {
					resp.sendError(HttpServletResponse.SC_CONFLICT,
							"No such user.");
				}
			} else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Bad formatted username and password");
			}

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String toJSON(Map<String, String> jsonMessage) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(jsonMessage);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
