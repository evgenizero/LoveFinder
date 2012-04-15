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

import com.neya.love.finder.bean.Conversation;
import com.neya.love.finder.db.MessagePersistor;
import com.neya.love.finder.tests.MessagePersistorTest;

public class GetConversationsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 175639863523262848L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			HashMap<String, String> jsonObject = new HashMap<String, String>();
			List<Conversation> conversations = null;

			MessagePersistor messagePersistor = new MessagePersistor();
			conversations = messagePersistor.getConversations(Integer
					.valueOf(req.getParameter("id")));
			ObjectMapper mapper = new ObjectMapper();
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			jsonObject.put("conversations",
					mapper.writeValueAsString(conversations));
			PrintWriter writer = resp.getWriter();
			writer.println(jsonObject);
			writer.flush();
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"No id provided or bad formatted");
		}
	}
}
