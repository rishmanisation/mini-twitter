//Message controller class
//Rishabh Ananthan

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

	private MessageDAO myMessageDAO;

	@Autowired
	public MessageController(MessageDAO myMessageDAO) {
		this.myMessageDAO = myMessageDAO;
	}

	//Function to write a new message
	@RequestMapping(value="/message/content={content}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ResponseEntity writeNewMessage(@PathVariable String content) {
		int status = myMessageDAO.addMessage(content);
		if (status == 1)
			return ResponseEntity.ok("{\"message\":\"Success!\"}");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Error!\"}");
	}

	//Function to publish messages by user and followers
	@RequestMapping(value="/message", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Messages> userTweets() {
		return myMessageDAO.getUserMessages();
	}

	//Search parameter
	@RequestMapping(value="/message/search={keyword}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Messages> searchUserTweets(@PathVariable String keyword) {
		return myMessageDAO.searchUserMessages(keyword);
	}
}
