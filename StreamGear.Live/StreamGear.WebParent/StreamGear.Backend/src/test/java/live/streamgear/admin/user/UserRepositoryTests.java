package live.streamgear.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import live.streamgear.common.entity.Role;
import live.streamgear.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests
{
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole()
	{
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userAdmin = new User("Admin@streamgear.live", "Password", "Sanders", "Riddle");
		userAdmin.addRole(roleAdmin);
		
		User savedUser = repo.save(userAdmin);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles()
	{
		User userTester = new User("Tester@streamgear.live", "Password", "Test", "User");
		
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userTester.addRole(roleEditor);
		userTester.addRole(roleAssistant);
		
		User savedUser = repo.save(userTester);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers()
	{
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	
	}
	
	@Test
	public void testGetUserById()
	{
		User user = repo.findById(1).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails()
	{
		User user = repo.findById(1).get();
		user.setEnabled(true);
		user.setEmail("Admin@streamgear.live");
		
		repo.save(user);
	}
	
	@Test
	public void testUpdateUserRoles()
	{
		User user = repo.findById(2).get();
		
		Role roleEditor = new Role(3);
		Role roleSales = new Role(2);
		
		user.getRoles().remove(roleEditor);
		user.addRole(roleSales);
		
		repo.save(user);
	}
	
	@Test
	public void testDeleteUser()
	{
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail()
	{
		String email = "Admin@streamgear.live";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById()
	{
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser()
	{
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser()
	{
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
}
