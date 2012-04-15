package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.neya.love.finder.bean.Conversation;
import com.neya.love.finder.bean.Message;
import com.neya.love.finder.db.MessagePersistor;

public class GetMessagesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4190254161173177993L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			HashMap<String, String> jsonObject = new HashMap<String, String>();
			List<Message> messages = null;
			MessagePersistor messagePersistor = new MessagePersistor();
			messages = messagePersistor.findMessagesBySenderReceiver(
					Integer.valueOf(req.getParameter("senderId")),
					Integer.valueOf(req.getParameter("receiverId")));

			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			
			ObjectMapper mapper = new ObjectMapper();
			jsonObject.put("messages", mapper.writeValueAsString(messages));
			PrintWriter writer = resp.getWriter();
			writer.println(jsonObject);
			writer.flush();
		} catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No id provided or bad formatted");
		}
		
	}
}
