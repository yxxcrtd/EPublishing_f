package cn.digitalpublishing.util.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DaxRequestWrapper extends HttpServletRequestWrapper {
	
	private Map parameters = null;

	public DaxRequestWrapper(HttpServletRequest request) {
		super(request);
		parameters = new HashMap(request.getParameterMap());
	}
	
	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	@Override
    public String getParameter(String name) {
        return parameters.containsKey(name)?parameters.get(name).toString():null;
    }

}
