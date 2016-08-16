package com.cooksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.entity.User;
import com.cooksys.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;

	public User getUserById(Long id) {
		return repo.findOne(id);
	}
	
	public User getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public User createUser(User user) {
		User newUser = getUserByUsername(user.getUsername());
		
		if (newUser == null) {
			repo.save(user);
			return user;
		}
		
		return null;
	}
	
	public User login(User user) {
		User login = getUserByUsername(user.getUsername());
		if(login == null)
			return null;
		if (login.getPassword().equals(user.getPassword()))
			return login;
		return null;
	}
}
