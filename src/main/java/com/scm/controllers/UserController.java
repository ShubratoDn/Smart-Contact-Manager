package com.scm.controllers;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scm.dao.ContactRepository;
import com.scm.dao.UserRepository;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.FileUploadHandler;
import com.scm.helpers.FileValidate;
import com.scm.helpers.ServerMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	private String classActive = "";

	@Autowired	
	private UserRepository userRepo;
	
	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private FileUploadHandler fileUpload;
	
	
	
	
	
	@ModelAttribute
	public void commonProperties( Model model,Principal principal) {
		
		User user =userRepo.getUserByEmail(principal.getName());		
		model.addAttribute("user", user);
		
		model.addAttribute("contact", new Contact());
	}
	
	
	
	//user home page
	@GetMapping(value= {"/home", "/",""})
	public String userHome(Model model, Principal principal) {
		//nav bar a active class dicchi
		classActive = "/user/home";		
		model.addAttribute("classActive", classActive);

		return "user/user_home";
	}
	
	
	
	//add contact request
	@GetMapping("/add-contact")
	public String addContactHandler(Model model) {
		classActive = "/add-contact";
		model.addAttribute("classActive", classActive);
		
		return "user/add_contact";
	}

	

	
	//add contact form info
	@PostMapping("/add-contact")
	public String addContact(
			@Valid @ModelAttribute("contact") Contact contact,
			BindingResult bres,
			@RequestParam("contact_image") MultipartFile file,
			Model model,
			Principal principal) {
		
		//nav bar er class
		classActive = "/add-contact";
		model.addAttribute("classActive", classActive);
		
		//error hoile field a value set kore dibe field gula te
		model.addAttribute("contact", contact);
		
		
		try {
			
			//getting the user logged in
			User userInfo =  userRepo.getUserByEmail(principal.getName());
			//data insert korte kaje dibe
			
			
			
			
			
			//validation check
			if(bres.hasErrors()) {				
				System.out.println("Has error" +  bres);
				return "user/add_contact";
			}
			
			
			
			String imgName = "contact.png";
			//file input handler			
			if(!file.isEmpty()) {								
				if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
					model.addAttribute("serverMessage", new ServerMessage("Only jpg, png file acceptable!", "error", "alert-danger"));					
					return "user/add_contact";
				}else {
					if(file.getSize() > 9000000) {
						model.addAttribute("serverMessage", new ServerMessage("Upload image under 1 mb!", "error", "alert-danger"));
						return "user/add_contact";
					}else {
						//=========================================
						//			UPLOADING FILE
						//=========================================
						String filePath = "src/main/resources/static/assets/img/contactImage/"+userInfo.getUser_id();
						String fileName = fileUpload.uploadFile(filePath, file);
						if(fileName != null) {
							//Image Name set kortesi
							imgName = userInfo.getUser_id()+fileName;							
						}else {
							model.addAttribute("serverMessage", new ServerMessage("Failed to upload File", "error", "alert-danger"));
							return "user/add_contact";
						}
					}
				}
			}
			
			
			if(contact.getDescription().isBlank()) {
				contact.setDescription("I'm a user of Smart Contact Manager");
			}
			
			
			
			
			//=====================================
			//inserting the data into table
			//=====================================
			
			
			
			//contact entity te user set kortesi
			contact.setUser(userInfo);
			//default image name
			contact.setImage(imgName);
			//user entity te contact ta add kortesi
			userInfo.getContacts().add(contact);			
						
			//inserting the user info
			User userRes = userRepo.save(userInfo);	
			
			model.addAttribute("serverMessage", new ServerMessage("Contact Added successful", "success", "alert-success"));
			//reseting contact info
			model.addAttribute("contact", new Contact());

			return "user/add_contact";
			
		} catch (Exception e) {
			System.out.println("Error " + e);
			model.addAttribute("serverMessage", new ServerMessage("Something went Wrong!!", "error", "alert-danger"));
			return "user/add_contact";
		}
		
	}
	
	
	
	
	
	//DEFAULT CONTACT PAGE
	@GetMapping(value =  {"/view-contacts/", "/view-contacts"})
	public String viewContactHandlerDef() {
		return "redirect:/user/view-contacts/page/1";
	}
	
	
	
	//view contacts with page number
	@GetMapping("/view-contacts/page/{pageNo}")
	public String viewContactHandlerWithPage(@PathVariable(value= "pageNo") Integer pageNo, Model model, Principal principal) {
		//navbar link
		classActive = "/view-contacts";
		model.addAttribute("classActive", classActive);
				
		//logged in user er information nicchi
		String userEmail = principal.getName();
		User user = userRepo.getUserByEmail(userEmail);	
		
		List<Contact> contactsCount = contactRepo.getContactsByUserId(user.getUser_id());
		
		//total contact ache...
		int totalRowCount = contactsCount.size();
		int perPageCount = 5;
		int totalPage = (totalRowCount / perPageCount)+1;
		int startsFrom = (pageNo-1) * perPageCount;
		
		List<Contact> contacts = contactRepo.getContactsByUserIdForPagination(user.getUser_id(), startsFrom, perPageCount);		
		
				

		model.addAttribute("contacts",contacts);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", pageNo);
		
		return "user/view_contact";
	}
	

	
	
	
	//a Single Contact
	@GetMapping("/contact/{contactId}")
	public String contactHandler(@PathVariable("contactId") int contactId,Principal principal, Model model) {
		//navbar link
		classActive = "/view-contacts";
		model.addAttribute("classActive", classActive);
		
		/*durgesh evabe korse*/
//		Optional<Contact> contactOptional = contactRepo.findById(contactId);
//		Contact contact = contactOptional.get();
		
		//user id ber korar jonno
		User user = userRepo.getUserByEmail(principal.getName());
		
		
		Contact contact = contactRepo.getContactWhereUseridAndIdMatches(user.getUser_id(), contactId);
//		System.out.println(contact.getName());
		
		model.addAttribute("contact", contact);
		
		return "user/contact";
	}

	
	
	
	//deleting contact	
	@PostMapping("/delete-contact")
	public String deleteContact(@RequestParam("contact_id") int conId, Principal principal) {
		try {			
			String email = principal.getName();
			User user = userRepo.getUserByEmail(email);
			
			Contact contact = contactRepo.findById(conId).get();
			
			if(contact.getUser().getUser_id() == user.getUser_id()) {
				//prev image delete?
				
				contactRepo.deleteById(conId);				
			}
			return "redirect:/home";
			
		}catch (Exception ex) {
			System.out.println("delete- conact method failur \n"+ex);
			return "redirect:/home";
		}		
	}
	
	
	
	//update contact information
	@PostMapping("/update-contact")
	public String updateURLhandle(@RequestParam("contact_id") int conId, Model model, HttpSession session) {
		
		Contact contact = contactRepo.findById(conId).get();
		System.out.println(contact.getCon_id());
		System.out.println(contact.getName());
		model.addAttribute("contact", contact);
		
		//jeno amra update korar somoy contact id khuje pai....porer method a use korsi
		session.setAttribute("update_contact_id", conId);
		
		return "user/update-contact";
	}
	
	
	@PostMapping("/updatenow")
	public String updateInfo(
			@Valid @ModelAttribute("contact") Contact contact,
			BindingResult bres,
			@RequestParam("contact_image") MultipartFile file,
			Model model,
			Principal principal,
			HttpSession session
			) {
		
		
		// ager method er session theke contact er id ta ansi		
		int conid = (int) session.getAttribute("update_contact_id");
		
		
		
		

		//error hoile field a value set kore dibe field gula te
		model.addAttribute("contact", contact);
		
		
		try {
			
			//getting the user logged in
			User userInfo =  userRepo.getUserByEmail(principal.getName());
			//data insert korte kaje dibe		
			
			Contact oldContact = contactRepo.findById(conid).get();
			
			
			
			//validation check
			if(bres.hasErrors()) {				
				System.out.println("Has error" +  bres);
				return "user/add_contact";
			}
			
			
			
			String imgName = "contact.png";
			//file input handler			
			if(!file.isEmpty()) {								
				if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
					model.addAttribute("serverMessage", new ServerMessage("Only jpg, png file acceptable!", "error", "alert-danger"));					
					return "user/add_contact";
				}else {
					if(file.getSize() > 9000000) {
						model.addAttribute("serverMessage", new ServerMessage("Upload image under 1 mb!", "error", "alert-danger"));
						return "user/add_contact";
					}else {
						//=========================================
						//			UPLOADING FILE
						//=========================================
						String filePath = "src/main/resources/static/assets/img/contactImage/"+userInfo.getUser_id();
						String fileName = fileUpload.uploadFile(filePath, file);
						if(fileName != null) {
							//Image Name set kortesi
							imgName = userInfo.getUser_id()+fileName;							
						}else {
							model.addAttribute("serverMessage", new ServerMessage("Failed to upload File", "error", "alert-danger"));
							return "user/add_contact";
						}
					}
				}
			}else {
				imgName = oldContact.getImage();
			}
			
			
			if(contact.getDescription().isBlank()) {
				contact.setDescription("I'm a user of Smart Contact Manager");
			}
			
			
			
			
			//=====================================
			//inserting the data into table
			//=====================================
			
			
			
			//contact entity te user set kortesi
			contact.setUser(userInfo);
			//default image name
			contact.setImage(imgName);
			
			
			
			
			//setting contact id
			contact.setCon_id(oldContact.getCon_id());
			
			
			
			
			
			//user entity te contact ta add kortesi
			userInfo.getContacts().add(contact);		
			
						
			//inserting the user info
			User userRes = userRepo.save(userInfo);
			
			
			model.addAttribute("serverMessage", new ServerMessage("Contact Updated successful", "success", "alert-success"));
			
			//reseting contact info
			//model.addAttribute("contact", new Contact());

			return "/user/contact";
			
		} catch (Exception e) {
			System.out.println("Error " + e);
			model.addAttribute("serverMessage", new ServerMessage("Something went Wrong!!", "error", "alert-danger"));
			return "user/update-contact";
		}
	}
	
	
}
