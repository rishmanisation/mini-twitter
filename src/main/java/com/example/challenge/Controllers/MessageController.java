/**
* MessageController.java: Message Controller class
* This is one of the two controllers that the web application uses. The functions
* in this controller are related to the messages sent by users. Methods are
* either HTTP GET or POST and the output is in JSON format.
* @author Rishabh Ananthan
* @version 1.0
*/

package com.example.challenge.Controllers;

import com.example.challenge.DAOs.MessageDAO;
import com.example.challenge.ResourceReps.People;
import com.example.challenge.ResourceReps.Followers;
import com.example.challenge.ResourceReps.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MessageController {

	// Instance of MessageDAO
	private MessageDAO myMessageDAO;

	/**
	* Constructor Function to initialize the beans
	* @author Rishabh Ananthan
	* @param myMessageDAO DAO
	*/
	@Autowired
	public MessageController(MessageDAO myMessageDAO) {
		this.myMessageDAO = myMessageDAO;
	}

	/**
	* Function to add a new user message
	* @author Rishabh Ananthan
	* @param content String containing the message content. This is also a
	* PathVariable. To add a message, append /message/content="message" to the
	* localhost URL, where "message" is the content of your message.
	* @return ResponseEntity JSON object
	*/
	@RequestMapping(value="/message/content={content}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity writeNewMessage(@PathVariable String content) {
		int status = myMessageDAO.addMessage(content);
		if (status == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	/**
	* Function to view the messages of the current user and his/her followers
	* @author Rishabh Ananthan
	* @return List of messages
	*/
	@RequestMapping(value="/message", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Messages> userTweets() {
		return myMessageDAO.getUserMessages();
	}

	/**
	* Function to filter user messages based on a keyword
	* @author Rishabh Ananthan
	* @param keyword String containing the keyword for filtering by.
	* @return Filtered list of messages.
	*/
	@RequestMapping(value="/message/search={keyword}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Messages> searchUserTweets(@PathVariable String keyword) {
		return myMessageDAO.searchUserMessages(keyword);
	}
}
