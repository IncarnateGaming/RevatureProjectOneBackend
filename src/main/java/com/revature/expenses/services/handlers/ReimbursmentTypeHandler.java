package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentTypeDAO;
import com.revature.expenses.models.ReimbursmentType;

public class ReimbursmentTypeHandler {
	private ReimbursmentTypeDAO repository = null;
	private static ReimbursmentType equipment;
	private static ReimbursmentType food;
	private static ReimbursmentType lodging;
	private static ReimbursmentType other;
	private static ReimbursmentType training;
	private static ReimbursmentType travel;
	public ReimbursmentTypeHandler() {
		super();
		this.repository = DAOUtilities.getReimbursmentTypeDao();
	}
	public ReimbursmentTypeHandler(ReimbursmentTypeDAO repository) {
		super();
		this.repository = repository;
	}
	public ReimbursmentType getEquipment() {
		if(equipment == null) {
			equipment = get("Equipment");
		}
		return equipment;
	}
	public ReimbursmentType getFood() {
		if(food == null) {
			food = get("Food");
		}
		return food;
	}
	public ReimbursmentType getLodging() {
		if(lodging == null) {
			lodging = get("Lodging");
		}
		return lodging;
	}
	public ReimbursmentType getOther() {
		if(other == null) {
			other = get("Other");
		}
		return other;
	}
	public ReimbursmentType getTraining() {
		if(training == null) {
			training = get("Training");
		}
		return training;
	}
	public ReimbursmentType getTravel() {
		if(travel == null) {
			travel = get("Travel");
		}
		return travel;
	}
	public ReimbursmentType create(ReimbursmentType reimbursmentTypeToCreate) {
		return repository.create(reimbursmentTypeToCreate);
	}
	public List<ReimbursmentType> list() {
		return repository.list();
	}
	public ReimbursmentType get(int reimbursmentTypeId) {
		if(reimbursmentTypeId <= 0)return null;
		return repository.get(reimbursmentTypeId);
	}
	public ReimbursmentType get(String type) {
		return repository.get(type);
	}
	public ReimbursmentType update(ReimbursmentType reimbursmentTypeToUpdate) {
		return repository.update(reimbursmentTypeToUpdate);
	}
	public boolean delete(ReimbursmentType reimbursmentTypeToDelete) {
		return repository.delete(reimbursmentTypeToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
