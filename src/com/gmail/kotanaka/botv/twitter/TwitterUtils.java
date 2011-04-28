package com.gmail.kotanaka.botv.twitter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterUtils {
	
	private static Logger LOG = Logger.getLogger(TwitterUtils.class.getName());
	
	public static List<String> findYouTubeTimeline(Twitter twitter) {
		// タイムラインを取得して返す
		// TODO 200件取得すると30秒以上かかって止まってしまう、少ない件数を問い合わせさせるべし
		Paging paging = new Paging(1, 20);
		ResponseList<Status> responses;
		try {
			responses = twitter.getHomeTimeline(paging);
		} catch (TwitterException e) {
			LOG.warning(e.getMessage());
			return null;
		}
		
		List<String> youTubeUrls = new ArrayList<String>();
		for (Iterator<Status> i = responses.iterator(); i.hasNext();) {
			Status status = i.next();
			String tweet = status.getText();
			List<String> result = findYouTubeURL(tweet);
			youTubeUrls.addAll(result);
		}
		return youTubeUrls;
	}
	
	public static String expandTinyURL(String tiny) {
		URL url;
		try {
			url = new URL(tiny);
		} catch (MalformedURLException e) {
			LOG.warning("URL " + tiny + " " + e.getMessage());
			return null;
		}
		
		HttpURLConnection connection;
		int code;
		
		try {
			connection = (HttpURLConnection) url.openConnection();
			code = connection.getResponseCode();
		} catch (IOException e) {
			LOG.warning("URL " + tiny + " " + e.getMessage());
			return null;
		}
		
		if (code != 200) {
			LOG.info("URL " + tiny + " cannot access. HTTP STATUS is " + code);
			return null;
		}
		
		URL expand = connection.getURL();
		connection.disconnect();
		
		return expand.toString();
	}
	
	 private static final Pattern STANDARD_URL_MATCH_PATTERN
	 = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+", 
			 Pattern.CASE_INSENSITIVE);

	public static List<String> findURL(String text) {
	    List<String> list = new ArrayList<String>();
	    Matcher matcher = STANDARD_URL_MATCH_PATTERN.matcher(text);
	    while(matcher.find()){
	    	list.add(matcher.group());
	    }
		return list;
	}

	public static List<String> findYouTubeURL(String text) {
		List<String> list = findURL(text);
		List<String> expand = new ArrayList<String>(list.size());
		for (Iterator<String> i = list.iterator(); i.hasNext();) {
			String string = getYouTubeURL(i.next());
			if (string != null) {
				expand.add(string);
			}
		}
		return expand;
	}

	public static String getYouTubeURL(String tiny) {
		String expand = expandTinyURL(tiny);
		if (expand == null) {
			return null;
		} else if (expand.startsWith("http://www.youtube.com/")) {
			return expand;
		} else {
			return null;
		}
	}

}
