package com.pocktalk;

public class URLHelper {

	public static final String BASE_URL = "http://pocktalk.herokuapp.com/";

	public static String login(String email) {
		return BASE_URL + "user/login?email=" + email;
	}

	/*public static String list(String userId) {
		return BASE_URL + "user/list?userId=" + userId;
	}*/

	public static String list(String userId) {
		return BASE_URL + "user/list";
	}
	
	public static String add(String userId, String url) {
		return BASE_URL + "api/add?userId=" + userId + "&url=" + url;
	}

}
