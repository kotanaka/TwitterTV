package com.gmail.kotanaka.botv;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.gmail.kotanaka.botv.twitter.TwitterUtils;

@SuppressWarnings("serial")
public class TwitterTimelineServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// セッションからTwitterオブジェクトを取得
		HttpSession session = req.getSession();
		Twitter twitter = (Twitter) session.getAttribute("Twitter");
		
		// もしまだなければ取得
		if (twitter == null) {
			TwitterAccount account = Utils.getTwitterAccount();
			if (account == null) {
				// Twitterの認証にリダイレクトする
				resp.sendRedirect("/twitter_login");
				return;
			}
			
			TwitterFactory factory = new TwitterFactory();
			twitter = factory.getInstance();
			twitter.setOAuthConsumer(Utils.CONSUMER_KEY, Utils.CONSUMER_SECRET);
			AccessToken token
				= new AccessToken(account.getAccessToken(), account.getAccessTokenSecret());
			twitter.setOAuthAccessToken(token);
			
			session.setAttribute("Twitter", twitter);
		}
		
		// タイムラインを取得して返す
		List<String> strings = TwitterUtils.findYouTubeTimeline(twitter);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		for (Iterator<String> i = strings.iterator(); i.hasNext();) {
			String url = i.next();
			resp.getWriter().println(url);
		}
	}
	
}
