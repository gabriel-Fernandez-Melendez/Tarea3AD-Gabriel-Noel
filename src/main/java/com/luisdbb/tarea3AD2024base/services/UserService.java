package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User entity) {
		return userRepository.save(entity);
	}

	public User update(User entity) {
		return userRepository.save(entity);
	}

	public void delete(User entity) {
		userRepository.delete(entity);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	public User find(Long id) {
		return userRepository.findById(id).get();
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public boolean authenticate(String username, String password) {
		User user = this.findByEmail(username);
		if (user == null) {
			return false;
		} else {
			if (password.equals(user.getPassword()))
				return true;
			else
				return false;
		}
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void deleteInBatch(List<User> users) {
		userRepository.deleteAll(users);
	}

}
