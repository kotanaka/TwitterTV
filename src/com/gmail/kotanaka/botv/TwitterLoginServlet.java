package com.gmail.kotanaka.botv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

@SuppressWarnings("serial")
public class TwitterLoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    Twitter twitter = new TwitterFactory().getInstance();
	    twitter.setOAuthConsumer(Utils.CONSUMER_KEY, Utils.CONSUMER_SECRET);
	    try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			
			// セッションに保存
			HttpSession session = req.getSession();
			session.setAttribute("RequestToken", requestToken);
			
			String authenticateUrl = requestToken.getAuthenticationURL();
			resp.sendRedirect(authenticateUrl);
		} catch (TwitterException e) {
			e.printStackTrace(resp.getWriter());
		}
		
	}

}
