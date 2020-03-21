package com.revature.expenses.api.templates;

import java.io.Serializable;
import java.util.Objects;

import com.revature.expenses.models.User;

public class UserTemplate implements Serializable{
	private static final long serialVersionUID = 4311208922121552923L;
	private User submitter;
	private User user;
	public UserTemplate() {
		super();
	}
	public UserTemplate(User submitter, User user) {
		super();
		this.submitter = submitter;
		this.user = user;
	}
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		return Objects.hash(submitter, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserTemplate)) {
			return false;
		}
		UserTemplate other = (UserTemplate) obj;
		return Objects.equals(submitter, other.submitter) && Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "UserTemplate [submitter=" + submitter + ", user=" + user + "]";
	}
}
