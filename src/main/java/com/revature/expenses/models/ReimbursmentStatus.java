package com.revature.expenses.models;

import java.io.Serializable;
import java.util.Objects;

public class ReimbursmentStatus implements Serializable{
	private static final long serialVersionUID = -5250790775324935132L;
	private int id;
	private String status;
	public ReimbursmentStatus(String status) {
		super();
		this.status = status;
	}
	public ReimbursmentStatus(int id, String status) {
		this(status);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ReimbursmentStatus)) {
			return false;
		}
		ReimbursmentStatus other = (ReimbursmentStatus) obj;
		return id == other.id && Objects.equals(status, other.status);
	}
	@Override
	public String toString() {
		return "ReimbursmentStatus [id=" + id + ", status=" + status + "]";
	}
}
