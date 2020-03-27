package com.revature.expenses.api.templates;

import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;

import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.User;

public class BlobTemplate {
	private Reimbursment reimbursment;
	private User submitter;
	private SerialBlob blob;
	public BlobTemplate() {
		super();
	}
	public BlobTemplate(Reimbursment reimbursment, User submitter, SerialBlob blob) {
		super();
		this.reimbursment = reimbursment;
		this.submitter = submitter;
		this.blob = blob;
	}
	public Reimbursment getReimbursment() {
		return reimbursment;
	}
	public void setReimbursment(Reimbursment reimbursment) {
		this.reimbursment = reimbursment;
	}
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public SerialBlob getBlob() {
		return blob;
	}
	public void setBlob(SerialBlob blob) {
		this.blob = blob;
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
		if (!(obj instanceof BlobTemplate)) {
			return false;
		}
		BlobTemplate other = (BlobTemplate) obj;
		return Objects.equals(reimbursment, other.reimbursment) && Objects.equals(submitter, other.submitter);
	}
	@Override
	public String toString() {
		return "BlobTemplate [reimbursment=" + reimbursment + ", submitter=" + submitter + "]";
	}
}