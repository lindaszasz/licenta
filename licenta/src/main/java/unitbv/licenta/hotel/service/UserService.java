package unitbv.licenta.hotel.service;

import unitbv.licenta.hotel.models.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
