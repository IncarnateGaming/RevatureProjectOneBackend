package com.revature.expenses.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter {

	/**
	 * Default Constructor
	 */
	private CORSFilter() {
	}
	
	public static void doFilter(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
//		res.addHeader("Access-Control-Allow-Origin","http://localhost:4200");
		res.setHeader("Access-Control-Allow-Origin","*");
		res.setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
		res.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		if(req.getMethod().equals("OPTIONS")) {
			res.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
	}
}
