/**
* MessageDAOclass.java: The class that implements the MessageDAO interface.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.DAOs;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.challenge.ResourceReps.People;
import com.example.challenge.ResourceReps.Messages;

@Repository
public class MessageDAOclass implements MessageDAO {
	private DataSource myDataSource; //DataSource instance
	private NamedParameterJdbcTemplate myJDBCTemplate; //Database instance

	/**
	* Constructor Function to initialize beans
	* @author Rishabh Ananthan
	* @param myDataSource Parameter to initialize the datasource.
	*/
	@Autowired
	public MessageDAOclass(DataSource myDataSource) {
		this.myDataSource = myDataSource;
		this.myJDBCTemplate = new NamedParameterJdbcTemplate(myDataSource);
	}

	/**
	* RowMapper that maps data from the Messages SQL table into Messages objects.
	* @author Rishabh Ananthan
	*/
	private RowMapper<Messages> myMessageMapper = (rs,rowNum) -> {
		Messages m = new Messages();
		m.setUserId(rs.getInt("person_id"));
		m.setMessage(rs.getString("content"));
		return m;
	};

	/**
	* Function that allows the current user to add a new message.
	* @author Rishabh Ananthan
	* @param content The content of the message to be added.
	* @return Number of rows affected if success, 0 otherwise.
	*/
	@Override
	public int addMessage(String content) {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("user_id",user_id);
		parameters.put("content",content);
		String sqlquery = "INSERT INTO messages (person_id, content) VALUES(:user_id, :content)";
		try {
			return myJDBCTemplate.update(sqlquery,parameters);
		}
		catch(DataIntegrityViolationException d) {
			return 0;
		}
	}

	/**
	* Function that lists all the messages of the current user and his/her
	* followers.
	* @author Rishabh Ananthan
	* @return List of all messages or current user and his/her followers.
	*/
	@Override
	public List<Messages> getUserMessages() {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("UserID",user_id);
		String sqlquery = "SELECT * FROM messages WHERE person_id=:UserID OR person_id IN (SELECT follower_person_id FROM followers WHERE person_id=:UserID)";
		List<Messages> result = myJDBCTemplate.query(sqlquery,parameters,myMessageMapper);
		return result;
	}

	/**
	* Function that searches user messages by keyword.
	* @author Rishabh Ananthan
	* @param keyword The word to filter messages by.
	* @return List of all messages or current user and his/her followers that
	* contain the keyword.
	*/
	@Override
	public List<Messages> searchUserMessages(String keyword) {
		int user_id = getId();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("UserID",user_id);
		String sqlquery = "SELECT * FROM messages WHERE (person_id=:UserID OR person_id IN (SELECT follower_person_id FROM followers WHERE person_id=:UserID)) AND content LIKE '%" + keyword + "%' ORDER BY id";
		List<Messages> result = myJDBCTemplate.query(sqlquery,parameters,myMessageMapper);
		return result;
	}

	/**
	* Function that returns the ID of the current user.
	* @author Rishabh Ananthan
	* @return ID of current user.
	*/
	public int getId() {
		String aux;
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
}
