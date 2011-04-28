package com.gmail.kotanaka.botv;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BoTVServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		TwitterAccount account = Utils.getTwitterAccount();
		if (account == null) {
			// Twitterの認証にリダイレクトする
			resp.sendRedirect("/twitter_login");
		} else {
			resp.setContentType("text/plain");
			resp.getWriter().println("TwitterのOAuthで承認されています。");
			resp.getWriter().println(account.getAccessToken());
			resp.getWriter().println(account.getAccessTokenSecret());
		}
	}
}
