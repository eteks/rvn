/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import java.io.Serializable;

/**
 *
 * @author ets-2
 */
public interface Interceptor extends Serializable {

    void destroy();

    void init();

    String intercept(ActionInvocation invocation) throws Exception;
}


