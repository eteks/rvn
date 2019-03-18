package com.model.pojo.acb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("signaltags_mapping")
public class SignalTagMapping extends Model {

	public int getSignalTagMapId() {
		return getInteger("id");
	}

	public int getSignalId() {
		return getInteger("signal_id");
	}

	public int getSignalTagId() {
		return getInteger("signaltags_id");
	}

	public SignalTagMapping() {

	}

	public SignalTagMapping(int signalId, int signalTagId, String createdDate) {
		set("signal_id", signalId);
		set("signaltags_id", signalTagId);
		set("created_date", createdDate);
	}
}
