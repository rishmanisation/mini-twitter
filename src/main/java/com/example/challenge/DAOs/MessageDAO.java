/**
* MessageDAO.java: Interface implementing the Data Access Object that contains
* functionality related to user messages. Individual function documentation is
* in MessageDAOclass.java.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.DAOs;

import java.util.List;
import com.example.challenge.ResourceReps.Messages;
import com.example.challenge.ResourceReps.People;

public interface MessageDAO {
	public int addMessage(String content);
	public List<Messages> getUserMessages();
	public List<Messages> searchUserMessages(String keyword);
	public int getId();
}
