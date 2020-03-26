package com.revature.expenses.api.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.ReimbursmentHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentBlobCommand extends FrontCommand{

	private ReimbursmentHandler reimbursmentHandler = new ReimbursmentHandler();
	private UserRoleHandler userRoleHandler = new UserRoleHandler();
	@Override
	public void process() throws ServletException, IOException {
		User submitter = null;
		if (req.getParameter("submitter") != null) {
			submitter = om.readValue(req.getParameter("submitter"),User.class);
		}
		int id = 0;
		try {
			id = Integer.parseInt(req.getParameter("reimbursmentId"));
		}catch(NumberFormatException e) {
			//No loggers here as the id will be 0 (invalid) and will be logged later.
		}
		Reimbursment reimbursment = null;
		if(submitter == null || submitter.getId() == 0) {
			LoggerSingleton.getAccessLog().warn("ReimbursmentBlobCommand: attempt to access without being logged in.");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else if ((id<=0) || (reimbursment = reimbursmentHandler.get(id)) == null) {
			LoggerSingleton.getExceptionLogger().warn("ReimbursmentBlobCommand: attempt to submit blob without a valid reimbursment id.");
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if(reimbursment.getAuthor() == null || (submitter.getId() != reimbursment.getAuthor().getId())&&(submitter.getRole() == null || submitter.getRole().getRole().equals(userRoleHandler.getAdmin()))){
			LoggerSingleton.getAccessLog().warn("ReimbursmentBlobCommand: attempt to access a receipt by: user: " + submitter.getId() + " username: " + submitter.getUsername());
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}else {
			if (type.equals("POST")) {//Get blob
				if((reimbursment = reimbursmentHandler.get(id))!= null) {
					try {
						for(byte blobPart : reimbursment.getReceipt().getBytes(1L,(int) reimbursment.getReceipt().length())) {
							out.print(blobPart);
						}
						res.setHeader("Content-Type", "image/jpeg");
						res.addHeader("mimeType", "image/jpeg");
					} catch (SerialException e) {
						res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						LoggerSingleton.getExceptionLogger().warn("ReimbursmentBlobCommand: serial exception in post: ", e);
					}
				}
			}else if (type.equals("PUT")) {//Update Blob
				SerialBlob blob;
				try {
					blob = new SerialBlob(body.getBytes());
					if(reimbursmentHandler.update(blob, id)) {
						LoggerSingleton.getBusinessLog().trace("ReimbursmentBlobCommand: user: " + submitter.getId() + " successfully changed the blob on: " + reimbursment);
						res.setStatus(HttpServletResponse.SC_CREATED);
					}else {
						LoggerSingleton.getExceptionLogger().warn("ReimbursmentBlobCommand: failure to process blob.");
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					}
				} catch (SerialException e) {
					LoggerSingleton.getExceptionLogger().warn("ReimbursmentBlobCommand: serial exception on blob extraction.");
					res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} catch (SQLException e) {
					LoggerSingleton.getExceptionLogger().warn("ReimbursmentBlobCommand: sql exception on blob extraction.");
					res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}else {
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				LoggerSingleton.getAccessLog().warn("ReimbursmentBlobCommand: Attempt to perform unallowed method: " + type + " Body: " + body);
			}
		}
	}
}