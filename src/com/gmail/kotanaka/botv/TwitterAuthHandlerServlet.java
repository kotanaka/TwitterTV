package com.gmail.kotanaka.botv;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@SuppressWarnings("serial")
public class TwitterAuthHandlerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// パラメータに入っているが、特に使用しない
		// String oauthToken = req.getParameter("oauth_token"); 
		String oauthVerifier = req.getParameter("oauth_verifier");

		// セッションからRequestTokenを取得
		HttpSession session = req.getSession();
		RequestToken requestToken = (RequestToken) session.getAttribute("RequestToken");
		
		resp.setContentType("text/plain");
		
		TwitterFactory twitterFactory = new TwitterFactory();
	    Twitter twitter = twitterFactory.getInstance();
	    twitter.setOAuthConsumer(Utils.CONSUMER_KEY, Utils.CONSUMER_SECRET);
	    
	    try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			TwitterAccount account = new TwitterAccount(
					user, accessToken.getToken(), accessToken.getTokenSecret());
			
			PersistenceManager pm = Utils.getPMF().getPersistenceManager();
			pm.makePersistent(account);
			pm.close();
			
			resp.getWriter().println("アクセストークンの保存に成功しました。");
			
		} catch (TwitterException e) {
			//e.printStackTrace(resp.getWriter());
			resp.getWriter().println(e.getMessage());
		}

	}

}
