package com.sai.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sai.springboot.entity.Entries;
import com.sai.springboot.entity.User;
import com.sai.springboot.service.EntriesService;
import com.sai.springboot.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyDiaryController {

	@Autowired
	HttpSession session;

	@Autowired
	private UsersService usersService;

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public UsersService getusersService() {
		return usersService;
	}

	public void setusersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Autowired
	private EntriesService entriesService;

	public EntriesService getentriesService() {
		return entriesService;
	}

	public void setentriesService(EntriesService entriesService) {
		this.entriesService = entriesService;
	}

	@GetMapping("/")
	public String homePage(Model model) {

		return "index";

	}

	@GetMapping("/home")
	public String loginPage(Model model) {

		return "loginpage";

	}

	@GetMapping("/register")
	public String registrationPage(Model model) {
		return "registrationpage";

	}

	@GetMapping("userhome")
	public String userHomePage(Model model) {

		String viewName = "userhomepage";

		User user = (User) session.getAttribute("user");

		List<Entries> entries = null;

		try {
			entries = entriesService.findByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("entrieslist", entries);

		return viewName;

	}

	@PostMapping(value = "saveuser")
	public String saveUser(@ModelAttribute("user") User user) {

		// Save the user details in the Database

		String viewName = "registersuccess";

		usersService.save(user);

		return viewName;

	}

	@PostMapping(value = "/authenticate")
	public String authenticteUser(@ModelAttribute("user") User user, Model model) {

		String viewName = "usernotexistorregistered";

		User dbUserDetails = usersService.findByUserName(user.getUsername());

		if (dbUserDetails != null && user.getPassword().equals(dbUserDetails.getPassword())) {

			viewName = "userhomepage";
			model.addAttribute("user", dbUserDetails);

			List<Entries> entries = null;

			session.setAttribute("user", dbUserDetails);

			try {
				entries = entriesService.findByUserId(dbUserDetails.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("entrieslist", entries);
		}

		return viewName;
	}

	@GetMapping("addentry")
	public String addEntry() {

		return "addentryform";
	}

	@PostMapping("saveentry")
	public String saveEntry(@ModelAttribute("entry") Entries entry, Model model) {

		String viewName = "userhomepage";

		entriesService.save(entry);

		User user = (User) session.getAttribute("user");

		List<Entries> entries = null;

		try {
			entries = entriesService.findByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("entrieslist", entries);

		return viewName;

	}

	@GetMapping("viewentry")
	public String viewEntry(@RequestParam("id") int id, Model model) {

		String viewName = "displayentry";
		Entries entry = entriesService.findById(id);
		model.addAttribute("entry", entry);

		User user = (User) session.getAttribute("user");

		if (user == null) {
			viewName = "loginpage";
		}

		return viewName;

	}

	@GetMapping("updateentry")
	public String updateEntry(@RequestParam("id") int id, Model model) {

		String viewName = "displayupdateentry";

		Entries entry = entriesService.findById(id);

		model.addAttribute("entry", entry);

		User user = (User) session.getAttribute("user");

		if (user == null) {
			viewName = "loginpage";
		}

		return viewName;

	}

	@PostMapping("processentryupdate")
	public String processUpdateEntry(@ModelAttribute("entry") Entries entry, Model model) {

		String viewName = "userhomepage";

		entriesService.update(entry);

		User user = (User) session.getAttribute("user");

		List<Entries> entries = null;

		try {
			entries = entriesService.findByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("entrieslist", entries);

		return viewName;

	}

	@GetMapping("deleteentry")
	public String deleteEntry(@RequestParam("id") int id, Model model) {

		String viewName = "userhomepage";

		Entries entry = entriesService.findById(id);
		entriesService.delete(entry);

		User user = (User) session.getAttribute("user");
		if (user == null) {
			viewName = "loginpage";
		}

		List<Entries> entries = null;

		try {
			entries = entriesService.findByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("entrieslist", entries);

		return viewName;

	}

	@GetMapping("signout")
	public String signout() {

		String viewName = "loginpage";
		session.invalidate();
		return viewName;

	}

}
