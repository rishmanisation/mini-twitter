//Class implementing the People resource
//Rishabh Ananthan

package com.example.challenge.ResourceReps;

public class People {
	private int people_id;
	private String handle;
	private String name;

	public People() {
	}

	public People(int people_id, String handle, String name) {
		this.people_id = people_id;
		this.handle = handle;
		this.name = name;
	}

	public int getId() {
		return people_id;
	}

	public void setId(int id) {
		this.people_id = id;
	}

	public String getHandle() {
		return handle;
	}

	public String getName() {
		return name;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public void setName(String name) {
		this.name = name;
	}
}
