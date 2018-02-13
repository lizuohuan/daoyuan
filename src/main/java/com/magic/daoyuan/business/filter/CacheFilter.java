package com.magic.daoyuan.business.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResp = (HttpServletResponse)response;
		httpResp.addHeader("Cache-Control", "max-age=0, private, must-revalidate");
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}


