package com.gmal.kotanaka.botv.twitter.test;

import java.util.List;

import junit.framework.TestCase;

import com.gmail.kotanaka.botv.twitter.TwitterUtils;


public class TwitterUtilsTest extends TestCase {
	
	/*
	public void testFindYouTubeTimeline() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(Utils.CONSUMER_KEY)
			.setOAuthConsumerSecret(Utils.CONSUMER_SECRET)
			.setUser("ko_tnk")
			.setPassword("hogehoge");
		TwitterFactory factory = new TwitterFactory(cb.build());
		Twitter twitter = factory.getInstance();
		List<String> youTubeUrls = TwitterUtils.findYouTubeTimeline(twitter);
		
		assertNotNull(youTubeUrls);
		
		for (Iterator<String> i = youTubeUrls.iterator(); i.hasNext();) {
			System.out.println(i.next());
		}
		
		assertTrue(true);
	}
	*/
	
	public void testFindURL() {
		String text = "あああhttp://bit.ly/en9NOoいいいhttp://bit.ly/33pdQTううう";
		String result1 = "http://bit.ly/en9NOo";
		String result2 = "http://bit.ly/33pdQT";
		List<String> urls = TwitterUtils.findURL(text);
		assertEquals(2, urls.size());
		assertTrue(urls.contains(result1));
		assertTrue(urls.contains(result2));
	}
	
	public void testExpandTinyURL() {
		String tiny = "http://bit.ly/en9NOo";
		String expand = "http://www.youtube.com/watch?v=2XJIOGx4UEk";
		String result = TwitterUtils.expandTinyURL(tiny);
		assertEquals(expand, result);
	}
	
	public void testFindYouTubeURL() {
		String text = "あああhttp://bit.ly/en9NOoいいいhttp://bit.ly/33pdQTうううhttp://bit.ly/hTui5xえええ";
		String result1 = "http://www.youtube.com/watch?v=2XJIOGx4UEk";
		String result2 = "http://www.youtube.com/watch?v=nQpexFBNYSQ";
		List<String> urls = TwitterUtils.findYouTubeURL(text);
		assertEquals(2, urls.size());
		assertTrue(urls.contains(result1));
		assertTrue(urls.contains(result2));
	}
	
	public void testGetYouTubeURL() {
		String tiny = "http://bit.ly/en9NOo";
		String expand = "http://www.youtube.com/watch?v=2XJIOGx4UEk";
		String result1 = TwitterUtils.getYouTubeURL(tiny);
		assertEquals(expand, result1);
		
		String noyt = "http://bit.ly/hTui5x";
		String result2 = TwitterUtils.getYouTubeURL(noyt);
		assertNull(result2);
	}

}
