/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_supervisor;

/**
 *
 * @author ets-2
 */
public class Vehicle {
        private int id;
	private String vehiclename;
        private String created_date;
	private int created_or_updated_by;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVehiclename() {
		return vehiclename;
	}
	public void setVehiclename(String vehiclename) {
		this.vehiclename = vehiclename;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public int getCreated_or_updated_by() {
		return created_or_updated_by;
	}
	public void setCreated_or_updated_by(int created_or_updated_by) {
		this.created_or_updated_by = created_or_updated_by;
	}
	
	public Vehicle(){}
	public Vehicle(String vehiclename, String created_date, int created_or_updated_by)
	{
		this.vehiclename=vehiclename;
		this.created_date=created_date;
		this.created_or_updated_by=created_or_updated_by;
	}
	
}
