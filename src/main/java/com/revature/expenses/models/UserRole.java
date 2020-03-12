package com.revature.expenses.models;

import java.io.Serializable;
import java.util.Objects;

public class UserRole implements Serializable {
	private static final long serialVersionUID = -1590930832839925757L;
	private int id;
	private String role;
	public UserRole(String role) {
		super();
		this.role = role;
	}
	public UserRole(int id, String role) {
		this(role);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, role);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserRole)) {
			return false;
		}
		UserRole other = (UserRole) obj;
		return id == other.id && Objects.equals(role, other.role);
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", role=" + role + "]";
	}
}
