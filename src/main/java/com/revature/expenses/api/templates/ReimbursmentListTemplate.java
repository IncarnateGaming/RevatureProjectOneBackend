package com.revature.expenses.api.templates;

import java.io.Serializable;
import java.util.Objects;

import com.revature.expenses.models.ReimbursmentStatus;
import com.revature.expenses.models.User;

public class ReimbursmentListTemplate implements Serializable{
	private static final long serialVersionUID = -7946231243545925132L;
	private User submitter;
	private User filterBy;
	private int limit;
	private int offset;
	private ReimbursmentStatus status;
	public ReimbursmentListTemplate() {
		super();
	}
	public ReimbursmentListTemplate(User submitter, User filterBy, int limit, int offset, ReimbursmentStatus status) {
		super();
		this.submitter = submitter;
		this.filterBy = filterBy;
		this.limit = limit;
		this.offset = offset;
		this.status = status;
	}
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public User getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(User filterBy) {
		this.filterBy = filterBy;
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
	public ReimbursmentStatus getStatus() {
		return status;
	}
	public void setStatus(ReimbursmentStatus status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(filterBy, limit, offset, status, submitter);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ReimbursmentListTemplate)) {
			return false;
		}
		ReimbursmentListTemplate other = (ReimbursmentListTemplate) obj;
		return Objects.equals(filterBy, other.filterBy) && limit == other.limit && offset == other.offset
				&& Objects.equals(status, other.status) && Objects.equals(submitter, other.submitter);
	}
	@Override
	public String toString() {
		return "ReimbursmentListTemplate [submitter=" + submitter + ", filterBy=" + filterBy + ", limit=" + limit
				+ ", offset=" + offset + ", status=" + status + "]";
	}
}