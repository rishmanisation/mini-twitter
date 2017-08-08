/**
* PeopleDAOclass.java: The class that implements the PeopleDAO interface.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.DAOs;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.challenge.ResourceReps.People;
import com.example.challenge.ResourceReps.Followers;

@Repository
public class PeopleDAOclass implements PeopleDAO {
	private DataSource myDataSource;  // DataSource instance
	private NamedParameterJdbcTemplate myJDBCTemplate; // Database instance

	/**
	* Constructor Function to initialize beans
	* @author Rishabh Ananthan
	* @param myDataSource Parameter to initialize the datasource.
	*/
	@Autowired
	public PeopleDAOclass(DataSource myDataSource) {
		this.myDataSource = myDataSource;
		this.myJDBCTemplate = new NamedParameterJdbcTemplate(myDataSource);
	}

	/**
	* RowMapper that maps data from the People SQL table into People objects.
	* @author Rishabh Ananthan
	*/
	private RowMapper<People> myPeopleMapper = (rs,rowNum) -> {
		People p = new People();
		p.setId(rs.getInt("id"));
		p.setHandle(rs.getString("handle"));
		p.setName(rs.getString("name"));
		return p;
	};

	/**
	* RowMapper that maps data from the Follower SQL table into Followers objects.
	* @author Rishabh Ananthan
	*/
	private RowMapper<Followers> myFollowerMapper = (rs,rowNum) -> {
		Followers f = new Followers();
		f.setUserId(rs.getInt("person_id"));
		f.setFollowerUserId(rs.getInt("follower_person_id"));
		return f;
	};

	/**
	* Function that allows a user to follow another user. The function returns the
	* number of rows affected if successful and 0 otherwise.
	* @author Rishabh Ananthan
	* @param follower_id ID of the person to be followed.
	* @return Integer indicating whether or not the action was successful.
	*/
	@Override
	public int follow(int follower_id) {
		int people_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>(); //HashMap to store parameters for the SQL query
		parameters.put("User",people_id);
		parameters.put("Follower",follower_id);
		String sqlquery = "INSERT INTO followers (person_id, follower_person_id) VALUES(:User,:Follower)";
		try {
			// If ID is not in the table, run the query and return the number of affected rows
			if(!isFollower(follower_id))
				return myJDBCTemplate.update(sqlquery,parameters);
			else
				return 0;
		}
		catch(DataIntegrityViolationException d) {
			return 0;
		}
	}

	/**
	* Function that allows a user to unfollow another user. The function returns the
	* number of rows affected if successful and 0 otherwise.
	* @author Rishabh Ananthan
	* @param follower_id ID of the person to be unfollowed.
	* @return Integer indicating whether or not the action was successful.
	*/
	@Override
	public int unfollow(int follower_id) {
		int people_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("User",people_id);
		parameters.put("Follower",follower_id);
		String sqlquery = "DELETE FROM followers WHERE person_id = :User AND follower_person_id = :Follower";
		try {
			return myJDBCTemplate.update(sqlquery,parameters);
		}
		catch(DataIntegrityViolationException d) {
			return 0;
		}
	}

	/**
	* Function that lists all the users.
	* @author Rishabh Ananthan
	* @return List of all users.
	*/
	@Override
	public List<People> listAllUsers() {
		String sqlquery = "SELECT * FROM people";
		/**Below function runs the query, maps each row to a people object and
		* stores all mapped objects in a list.
		*/
		List<People> result = myJDBCTemplate.query(sqlquery,myPeopleMapper);
		return result;
	}

	/**
	* Function that lists all the users that the current user is following.
	* @author Rishabh Ananthan
	* @return List of all users being followed by the current user.
	*/
	@Override
	public List<Followers> listAllFollowers() {
		int follower_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("follower_id",follower_id);
		String sqlquery = "SELECT person_id, follower_person_id FROM followers WHERE follower_person_id = :follower_id ORDER BY person_id";
		List<Followers> result = myJDBCTemplate.query(sqlquery,parameters,myFollowerMapper);
		return result;
	}

	/**
	* Function that lists all the users that are following the current user.
	* @author Rishabh Ananthan
	* @return List of all users following the current user.
	*/
	@Override
	public List<Followers> listAllFollowing() {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("user_id",user_id);
		String sqlquery = "SELECT person_id, follower_person_id FROM followers WHERE person_id = :user_id ORDER BY follower_person_id";
		List<Followers> result = myJDBCTemplate.query(sqlquery,parameters,myFollowerMapper);
		return result;
	}

	/**
	* Function that lists all the users along with their most popular follower.
	* @author Rishabh Ananthan
	* @return List of all users with their most popular follower.
	*/
	public List<Followers> listUsersWithMostPopularFollower() {
		String sqlquery = "SELECT f2.person_id,(SELECT f1.follower_person_id FROM followers f1 WHERE f1.person_id=f2.person_id GROUP BY f1.follower_person_id ORDER BY COUNT(f1.follower_person_id) DESC LIMIT 1) AS follower_person_id FROM followers f2 GROUP BY f2.person_id";
		List<Followers> result = myJDBCTemplate.query(sqlquery,myFollowerMapper);
		return result;
	}

	/**
	* Function that returns the ID of the current user.
	* @author Rishabh Ananthan
	* @return ID of current user.
	*/
	public int getId() {
		String aux;

		//Below block returns the username of the current user as a string
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			aux = ((UserDetails)principal).getUsername();
		else
			aux = principal.toString();
			
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("handle",aux);
		String sqlquery = "SELECT id FROM people WHERE handle = :handle";
		int Id = myJDBCTemplate.queryForObject(sqlquery,parameters,Integer.class);
		return Id;
	}

	/**
	* Function that whether the current user follows another user.
	* @author Rishabh Ananthan
	* @param follower_id ID of the user we are checking.
	* @return boolean variable that indicates whether or not the current user
	* already follows the person.
	*/
	public boolean isFollower(int follower_id) {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("user",user_id);
		parameters.put("follower",follower_id);
		String sqlquery = "SELECT COUNT(1) FROM followers WHERE person_id = :user AND follower_person_id = :follower";
		Long l = myJDBCTemplate.queryForObject(sqlquery,parameters,Long.class);
		return l > 0;
	}
}
