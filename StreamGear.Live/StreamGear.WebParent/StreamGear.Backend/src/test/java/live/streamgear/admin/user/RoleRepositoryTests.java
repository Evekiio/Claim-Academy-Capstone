package live.streamgear.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import live.streamgear.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests
{
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole()
	{
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repo.save(roleAdmin);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles()
	{
		Role roleSales = new Role("Sales", "Manage Product Price, Customers, Shipping, Orders, and Reports");
		Role roleEditor = new Role("Editor", "Manage Categories, Brands, Products, Articles, and Menus");
		Role roleShipper = new Role("Shipper", "View Products, Orders, and Update Order Status");
		Role roleAssistant = new Role("Assistant", "Manage Questions and Reviews");
		
		repo.saveAll(List.of(roleSales, roleEditor, roleShipper, roleAssistant));
	}
}
