package com.revature.expenses.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;

import com.revature.expenses.services.handlers.ReimbursmentHandler;
import com.revature.expenses.services.helpers.MathHelper;

public class Reimbursment implements Serializable{
	private static final long serialVersionUID = -9082304697143329368L;
	private int id;
	private double amount;
	private String description;
	private Date submitted;
	private Date resolved;
	private User author;
	private User resolver;
	private ReimbursmentStatus status;
	private ReimbursmentType type;
	public Reimbursment() {
		super();
	}
	public Reimbursment(double amount,User author, ReimbursmentStatus status, ReimbursmentType type) {
		setAmount(amount);
		this.author = author;
		this.status = status;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		amount = MathHelper.doubleRoundDown2(amount);
		if(amount < 0) {amount *= -1;}
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getResolved() {
		return resolved;
	}
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	public SerialBlob receiveReceipt() {
		return new ReimbursmentHandler().getBlob(this.id);
	}
	public void sendReceipt(SerialBlob receipt) {
		new ReimbursmentHandler().update(receipt, this.id);
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getResolver() {
		return resolver;
	}
	public void setResolver(User resolver) {
		this.resolver = resolver;
	}
	public ReimbursmentStatus getStatus() {
		return status;
	}
	public void setStatus(ReimbursmentStatus status) {
		this.status = status;
	}
	public ReimbursmentType getType() {
		return type;
	}
	public void setType(ReimbursmentType type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, author, description, id, resolved, resolver, status, submitted, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Reimbursment)) {
			return false;
		}
		Reimbursment other = (Reimbursment) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(author, other.author) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(resolved, other.resolved)
				&& Objects.equals(resolver, other.resolver) && Objects.equals(status, other.status)
				&& Objects.equals(submitted, other.submitted) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "Reimbursment [id=" + id + ", amount=" + amount + ", description=" + description + ", submitted="
				+ submitted + ", resolved=" + resolved + ", author=" + author + ", resolver=" + resolver + ", status="
				+ status + ", type=" + type + "]";
	}
}
