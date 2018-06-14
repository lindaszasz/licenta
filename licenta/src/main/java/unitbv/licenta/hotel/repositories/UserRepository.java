package unitbv.licenta.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unitbv.licenta.hotel.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 Iterable<User> getById(long id);
	 Iterable<User> getByLastNameOrFirstName(String lastName, String firstName);
}
