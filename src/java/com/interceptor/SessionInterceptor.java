/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;

/**
 *
 * @author ets-2
 */
public class SessionInterceptor extends AbstractInterceptor {
  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
      System.out.println("intercept method called");
      Map<String,Object> session = invocation.getInvocationContext().getSession();
      if(session.isEmpty())
          return "session"; // session is empty/expired
      return invocation.invoke();
  }
}