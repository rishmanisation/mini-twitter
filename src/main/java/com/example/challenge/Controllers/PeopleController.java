//People Controller class
//Rishabh Ananthan

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

	private PeopleDAO myPeopleDAO;

	@Autowired
	public PeopleController(PeopleDAO myPeopleDAO) {
		this.myPeopleDAO = myPeopleDAO;
	}

	//Follow User
	@RequestMapping(value="/people/follow/{follower_id}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity followUser(@PathVariable int follower_id) {
		int follow = myPeopleDAO.follow(follower_id);
		if (follow == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	//Unfollow user
	@RequestMapping(value="/people/unfollow/{follower_id}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity unfollowUser(@PathVariable int follower_id) {
		int unfollow = myPeopleDAO.unfollow(follower_id);
		if (unfollow == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	//List all users
	@RequestMapping(value="/people", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<People> listUsers() {
		return myPeopleDAO.listAllUsers();
	}

	//List everybody following a user
	@RequestMapping(value="/followers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listFollowers() {
		return myPeopleDAO.listAllFollowers();
	}

	//List everybody a user is following
	@RequestMapping(value="/following", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listFollowing() {
		return myPeopleDAO.listAllFollowing();
	}

	//List users along with their most popular follower (arbitary selection in case of a tie)
	@RequestMapping(value="/option2", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Followers> listUsersWithTheirMostPopularFollower() {
		return myPeopleDAO.listUsersWithMostPopularFollower();
	} 
}
