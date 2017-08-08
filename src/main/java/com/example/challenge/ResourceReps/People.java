/**
* People.java: Class implementing the People resource rep. This class implements
* basic get/set functionality.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.ResourceReps;

public class People {
	private int people_id; // ID of the user
	private String handle; // Username
	private String name; // Name of the user.

	/**
	* Default constructor function.
	* @author Rishabh Ananthan
	*/
	public People() {
	}

	/**
	* Constructor function.
	* @author Rishabh Ananthan
	* @param people_id ID of the user
	* @param handle Username
	* @param name Name of the user
	*/
	public People(int people_id, String handle, String name) {
		this.people_id = people_id;
		this.handle = handle;
		this.name = name;
	}

 /**
 * Function that returns the ID of the user.
 * @return User ID
 */
	public int getId() {
		return people_id;
	}

	/**
  * Function that sets the ID of the user.
  * @param id User ID
  */
	public void setId(int id) {
		this.people_id = id;
	}

	/**
  * Function that returns the username.
  * @return Username
  */
	public String getHandle() {
		return handle;
	}

	/**
  * Function that sets the username.
  * @param handle Username
  */
	public void setHandle(String handle) {
		this.handle = handle;
	}

	/**
  * Function that returns the name of the user.
  * @return Name of the user
  */
	public String getName() {
		return name;
	}

	/**
  * Function that sets the name of the user.
  * @param name Name of the user
  */
	public void setName(String name) {
		this.name = name;
	}
}
