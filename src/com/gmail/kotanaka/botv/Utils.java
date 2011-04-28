package com.gmail.kotanaka.botv;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Utils {
	
	public static final String CONSUMER_KEY = "bEJP7ZTw8JNM97EeJ5AXQ";
	
	public static final String CONSUMER_SECRET = "DNV1gAvinj0L8jRy2cZQWp6efOZu19TKc1Wc5Dh1ms";
	
	private static final PersistenceManagerFactory pmfInstance
		= JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private Utils() {}
	
	public static PersistenceManagerFactory getPMF() {
		return pmfInstance;
	}
	
	public static User getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	public static TwitterAccount getTwitterAccount() {
		User user = getCurrentUser();
		// Twitterの認証をしているか確認する
		PersistenceManager pm = Utils.getPMF().getPersistenceManager();
		Query query = pm.newQuery(TwitterAccount.class);
		query.setFilter("user == userParam");
		query.declareParameters("com.google.appengine.api.users.User userParam");
		
		@SuppressWarnings("unchecked")
		List<TwitterAccount> accounts = (List<TwitterAccount>) query.execute(user);
		TwitterAccount account;
		if (accounts.isEmpty()) {
			account = null;
		} else {
			account = accounts.iterator().next();
		}
		
		pm.close();
		return account;
	}
	
}
