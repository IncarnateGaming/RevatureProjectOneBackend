package com.revature.expenses.api.templates;

import java.io.Serializable;
import java.util.Objects;

import com.revature.expenses.models.User;

public class UserListTemplate implements Serializable{
	private static final long serialVersionUID = -729766724736559562L;
	private User submitter;
	private int limit;
	private int offset;
	public UserListTemplate() {
		super();
	}
	public UserListTemplate(User submitter, int limit, int offset) {
		super();
		this.submitter = submitter;
		this.limit = limit;
		this.offset = offset;
	}
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	@Override
	public int hashCode() {
		return Objects.hash(limit, offset, submitter);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserListTemplate)) {
			return false;
		}
		UserListTemplate other = (UserListTemplate) obj;
		return limit == other.limit && offset == other.offset && Objects.equals(submitter, other.submitter);
	}
	@Override
	public String toString() {
		return "UserListTemplate [submitter=" + submitter + ", limit=" + limit + ", offset=" + offset + "]";
	}
}