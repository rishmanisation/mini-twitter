//Class implementing the Followers resource
//Rishabh Ananthan

package com.example.challenge.ResourceReps;

public class Followers {
	private int user_id;
	private int follower_user_id;

	public Followers() {

	}

	public Followers(int user_id, int follower_user_id) {
		this.user_id = user_id;
		this.follower_user_id = follower_user_id;
	}

	public int getUserId() {
		return user_id;
	}

	public int getFollowerUserId() {
		return follower_user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public void setFollowerUserId(int follower_user_id) {
		this.follower_user_id = follower_user_id;
	}
}
