package com.example.challenge.DAOs;

import java.util.List;
import com.example.challenge.ResourceReps.People;
import com.example.challenge.ResourceReps.Followers;

public interface PeopleDAO {
	int follow(int follower_id);
	int unfollow(int follower_id);
	List<People> listAllUsers();
	List<Followers> listAllFollowers();
	List<Followers> listAllFollowing();
	List<Followers> listUsersWithMostPopularFollower();
	int getId();
	boolean isFollower(int follower_id);
}
