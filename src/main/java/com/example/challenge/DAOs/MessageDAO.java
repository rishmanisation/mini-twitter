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
