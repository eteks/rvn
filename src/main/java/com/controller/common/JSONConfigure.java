package com.controller.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;


public class JSONConfigure {
	static HttpServletRequest request ;
	
	
	 public static String getAngularJSONFile()
	 {
		 try {
			 request  =  ServletActionContext.getRequest();
				String filename = IOUtils.toString(request.getInputStream());
//				System.out.println("fileee "+filename);
				
				return filename;
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			} 
		
	 }

}
