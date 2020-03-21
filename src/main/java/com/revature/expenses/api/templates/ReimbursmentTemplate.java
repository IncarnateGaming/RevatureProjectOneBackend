package com.revature.expenses.api.templates;

import java.io.Serializable;
import java.util.Objects;

import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.User;

public class ReimbursmentTemplate implements Serializable {
	private static final long serialVersionUID = -8940611961335451312L;
	private User submitter;
	private Reimbursment reimbursment;
	public ReimbursmentTemplate() {
		super();
	}
	public ReimbursmentTemplate(User submitter, Reimbursment reimbursment) {
		super();
		this.submitter = submitter;
		this.reimbursment = reimbursment;
	}
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public Reimbursment getReimbursment() {
		return reimbursment;
	}
	public void setReimbursment(Reimbursment reimbursment) {
		this.reimbursment = reimbursment;
	}
	@Override
	public int hashCode() {
		return Objects.hash(reimbursment, submitter);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ReimbursmentTemplate)) {
			return false;
		}
		ReimbursmentTemplate other = (ReimbursmentTemplate) obj;
		return Objects.equals(reimbursment, other.reimbursment) && Objects.equals(submitter, other.submitter);
	}
	@Override
	public String toString() {
		return "ReimbursmentTemplate [submitter=" + submitter + ", reimbursment=" + reimbursment + "]";
	}
}