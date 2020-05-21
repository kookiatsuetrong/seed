package web;
import java.sql.*;
import java.util.*;
import java.security.*;
import javax.persistence.*;
import javax.servlet.http.*;
import org.springframework.ui.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
class Main {

	@GetMapping("/")
	String showHome() {
		return "index";
	}

	@GetMapping("/login")
	String showLogInPage(HttpSession session) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "login";
		} else {
			return "redirect:/profile";
		}
	}

	@PostMapping("/login")
	String checkPassword(HttpSession session, String email, String password) {
		Object o = session.getAttribute("user");
		if (o == null) {
			EntityManager manager = factory.createEntityManager();
			Query query = manager.createQuery("select u from User u   " +
											"  where u.email    = :e  " +
											"  and   u.password = :p  ");
			query.setParameter("e", email);
			query.setParameter("p", encrypt(password));
			List result = query.getResultList();
			if (result.size() == 1) {
				User user = (User) result.get(0);
				session.setAttribute("user", user);
			}
			manager.close();
		}
		return "redirect:/profile";
	}

	@GetMapping("/profile")
	String showProfilePage(HttpSession session) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/login";
		} else {
			return "profile";
		}
	}

	@GetMapping("/register")
	String showRegisterPage(HttpSession session) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "register";
		} else {
			return "redirect:/profile";
		}
	}

	@PostMapping("/register")
	String registerUser(String email, String password,
						String first, String last) {
		EntityManager manager = factory.createEntityManager();
		User u = new User();
		u.email = email;
		u.firstName = first;
		u.lastName = last;
		u.password = encrypt(password);
		manager.getTransaction().begin();
		manager.persist(u);
		manager.getTransaction().commit();
		manager.close();
		return "redirect:/login";
	}

	@GetMapping("/logout")
	String showLogOutPage(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");

	@GetMapping("/test-result")
	String showUser(Model model) {
		try {
			EntityManager manager = factory.createEntityManager();
			Query query = manager.createQuery("select u from User u");
			List all = query.getResultList();
			model.addAttribute("data", all);
			manager.close();
		} catch (Exception e) { }
		return "list";
	}

	static String encrypt(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA3-512");
			byte[] data = digest.digest(s.getBytes());
			String t = "";
			for (byte b : data) {
				int k = b;
				if (k < 0) {
					k = 256 + k;
				}
				t += String.format("%X", k);
			}
			return t;
		} catch (Exception e) { }

		return "EncryptionError";
	}
}

/*
Coding Standard
+ Prefer TAB to space
+ User plural form for array and list
*/