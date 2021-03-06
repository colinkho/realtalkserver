package com.realtalkserver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.realtalkserver.util.RequestParameters;
import com.realtalkserver.util.ResponseParameters;
import com.realtalkserver.util.UserManager;
/**
 * AuthenticateServlet is a servlet used for authenticating a user.
 * 
 * @author Taylor Williams
 *
 */

@SuppressWarnings("serial")
public class AuthenticateServlet extends BaseServlet {
    /**
     * doPost handles a post request from to the server. This adds a user
     * to the server and returns an appropriate response in JSON.
     * 
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    	//user info
    	logger.log(Level.INFO, "Retrieving User Information");
        String stRegId = getParameter(req, RequestParameters.PARAMETER_REG_ID);
        String stUser = getParameter(req, RequestParameters.PARAMETER_USER);
        String stPwd = getParameter(req, RequestParameters.PARAMETER_PWORD);
        logger.log(Level.INFO, "Retrieval Successful");
        
        // Authenticate and generate response to indicate if successful
        logger.log(Level.INFO, "Processing Authenticate Request to Database");
        boolean fAuthenticated = UserManager.fAuthenticateUser(stUser, stPwd);
        logger.log(Level.INFO, "Request completed");
        
        // Generate JSON response
        JSONObject jsonResponse = new JSONObject();
        try {
        	String stSuccessMsg = fAuthenticated ? "true" : "false";
        	jsonResponse.put(RequestParameters.PARAMETER_SUCCESS, stSuccessMsg);
        	jsonResponse.put(ResponseParameters.PARAMETER_ERROR_CODE, ResponseParameters.RESPONSE_ERROR_CODE_USER);
        	jsonResponse.put(ResponseParameters.PARAMETER_ERROR_MSG, ResponseParameters.RESPONSE_MESSAGE_USER_ERROR);
        } catch (JSONException e) {
        	// Exception will never be thrown as key is not null.
        }
        logger.log(Level.INFO, "Setting up response successful");
        
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write(jsonResponse.toString());
        setSuccess(resp);
        logger.log(Level.INFO, "POST Request to Authenticate completed");
    }
}
