package org.bossky.jpademo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "EVENTS")
public class Event implements org.bossky.jpa.Entity {
	@Id
	protected String name;
	@Column
	protected Date createTime;

	public Event() {

	}

	public Event(String name) {
		this.name = name;
		createTime = new Date();
	}

	@Override
	public String getId() {
		return name;
	}
}
