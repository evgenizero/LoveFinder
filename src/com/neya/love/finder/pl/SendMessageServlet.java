package com.neya.love.finder.pl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.bean.Message;
import com.neya.love.finder.db.CustomerPersistor;
import com.neya.love.finder.db.MessagePersistor;

public class SendMessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1521104639843109372L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			Message message = mapper.readValue(req.getParameter("message"),
					Message.class);
			MessagePersistor messagePersistor = new MessagePersistor();

			boolean created = messagePersistor.addMessage(message);
			if (created) {
				HashMap<String, String> jsonObject = new HashMap<String, String>();

				resp.setStatus(HttpServletResponse.SC_CREATED);

				jsonObject.put("messages", mapper
						.writeValueAsString(messagePersistor
								.findMessagesBySenderReceiver(
										message.getMessageSenderId(),
										message.getMessageReceiverId())));

				PrintWriter printWriter = resp.getWriter();
				printWriter.println(jsonObject);
				printWriter.println();
			} else {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error sending message.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Error parsing message data");
		}
	}
}
