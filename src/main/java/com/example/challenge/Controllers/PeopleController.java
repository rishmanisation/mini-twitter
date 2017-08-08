/**
* PeopleController.java: People Controller class.
* This is one of two controllers used by the web application. The functions
* implemented are related to the users. Methods are either HTTP GET or POST
* and the output is in JSON format.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.Controllers;

import com.example.challenge.DAOs.PeopleDAO;
import com.example.challenge.ResourceReps.People;
import com.example.challenge.ResourceReps.Followers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
public class PeopleController {

	// Instance of PeopleDAO
	private PeopleDAO myPeopleDAO;

	/**
	* Constructor function to initialize the beans
	* @author Rishabh Ananthan
	* @param myPeopleDAO DAO
	*/
	@Autowired
	public PeopleController(PeopleDAO myPeopleDAO) {
		this.myPeopleDAO = myPeopleDAO;
	}

	/**
	* Function that allows current user to follow another user.
	* @author Rishabh Ananthan
	* @param follower_id ID of the user to be followed. This is a path variable.
	* @return JSON object
	*/
	@RequestMapping(value="/people/follow/{follower_id}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity followUser(@PathVariable int follower_id) {
		int follow = myPeopleDAO.follow(follower_id);
		if (follow == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	/**
	* Function that allows the current user to unfollow another user.
	* @author Rishabh Ananthan
	* @param follower_id ID of the user to be unfollowed. This is a path variable.
	* @return JSON object
	*/
	@RequestMapping(value="/people/unfollow/{follower_id}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity unfollowUser(@PathVariable int follower_id) {
		int unfollow = myPeopleDAO.unfollow(follower_id);
		if (unfollow == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	/**
	* Function that lists all the available users.
	* @author Rishabh Ananthan
	* @return List of all users.
	*/
	@RequestMapping(value="/people", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<People> listUsers() {
		return myPeopleDAO.listAllUsers();
	}

	/**
	* Function that lists all the followers of the current user.
	* @author Rishabh Ananthan
	* @return List of all followers.
	*/
	@RequestMapping(value="/followers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listFollowers() {
		return myPeopleDAO.listAllFollowers();
	}

	/**
	* Function that lists all the people the current user is following.
	* @author Rishabh Ananthan
	* @return List of all users the current user is following.
	*/
	@RequestMapping(value="/following", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listFollowing() {
		return myPeopleDAO.listAllFollowing();
	}

	/**
	* Function that lists all users along with their most popular follower. The
	* most popular follower is a person who the user follows, that has the most
	* followers. In case two or more people have the highest number of followers,
	* an arbitrary selection is made.
	* @author Rishabh Ananthan
	* @return List of users along with their most popular follower
	*/
	@RequestMapping(value="/option2", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listUsersWithTheirMostPopularFollower() {
		return myPeopleDAO.listUsersWithMostPopularFollower();
	}
}
