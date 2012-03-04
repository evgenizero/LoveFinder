package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CustomerData customer = null;

		try {
			int customerId;

			if (req.getParameter("customerId") != null) {
				customerId = Integer.parseInt(req.getParameter("customrId"));
			} else {
				customerId = 0;
			}

			if (customerId > 0) {
				CustomerPersistor customerPersistor = new CustomerPersistor();
				customer = customerPersistor.findById(customerId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter printWriter = resp.getWriter();
		printWriter.println(JSONSerializer.toJSON(customer));
	}

}
