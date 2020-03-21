package com.revature.expenses.models;

import java.io.Serializable;
import java.util.Objects;

public class ReimbursmentType implements Serializable{
	private static final long serialVersionUID = -7535642665977135280L;
	private int id;
	private String type;
	public ReimbursmentType() {
		super();
	}
	public ReimbursmentType(String type) {
		super();
		this.type = type;
	}
	public ReimbursmentType(int id, String type) {
		this(type);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ReimbursmentType)) {
			return false;
		}
		ReimbursmentType other = (ReimbursmentType) obj;
		return id == other.id && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "ReimbursmentType [id=" + id + ", type=" + type + "]";
	}
}
