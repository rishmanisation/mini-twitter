package com.example.challenge.ResourceReps;

public class Messages {
	private int user_id;
	private String message;

	public Messages() {

	}

	public Messages(int user_id, String message) {
		this.user_id = user_id;
		this.message = message;
	}

	public int getUserId() {
		return user_id;
	}

	public String getMessage() {
		return message;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
