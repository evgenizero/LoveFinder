/**
 * Servlet that receive information 
 * about customer and add it to the db
 * 
 * @author <a href='mailto:yanev93@gmal.com'>Nikolay Yanev</a>
 * @date 14 Dec 2011
 */
package com.neya.love.finder.pl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.TextNode;

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

		try {
			ObjectMapper mapper = new ObjectMapper();
			CustomerData customer = mapper.readValue(req.getParameter("user"),
					CustomerData.class);
			CustomerPersistor customerPersistor = new CustomerPersistor();
			
			//if (customerPersistor.isFreeUsername(customer.getUsername())) {
				int customerId = customerPersistor.addCustomer(customer);
				if (customerId > 0) {
					HashMap<String, String> jsonObject = new HashMap<String, String>();
					
					resp.setStatus(HttpServletResponse.SC_CREATED);
					
					jsonObject.put("customer_id", String.valueOf(customerId));
					
					PrintWriter printWriter = resp.getWriter();
					printWriter.println(jsonObject);
					printWriter.println();
				} else {
					resp.sendError(
							HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Error creating user.");
				}
//			} else {
//				resp.sendError(HttpServletResponse.SC_CONFLICT,
//						"Username already exists.");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Error parsing user data");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
