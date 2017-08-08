/**
* Followers.java: Class implementing the Followers resource rep. This class implements
* basic get/set functionality.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.ResourceReps;

public class Followers {
	private int user_id; // ID of the user
	private int follower_user_id; // ID of the follower

	/**
	* Default constructor function.
	* @author Rishabh Ananthan
	*/
	public Followers() {

	}

	/**
	* Constructor function.
	* @author Rishabh Ananthan
	* @param user_id ID of the user
	* @param follower_id ID of the follower
	*/
	public Followers(int user_id, int follower_user_id) {
		this.user_id = user_id;
		this.follower_user_id = follower_user_id;
	}

	/**
  * Function that returns the ID of the user.
  * @return User ID
  */
	public int getUserId() {
		return user_id;
	}

	/**
	* Function that sets the ID of the user.
	* @param user_id User ID
	*/
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	/**
	* Function that gets the ID of the follower.
	* @return Follower User ID
	*/
	public int getFollowerUserId() {
		return follower_user_id;
	}

	/**
	* Function that sets the ID of the follower.
	* @param follower_user_id Follower User ID
	*/
	public void setFollowerUserId(int follower_user_id) {
		this.follower_user_id = follower_user_id;
	}
}
