package jackson.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringRootConfig.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserTest {
	private static Logger LOGGER = LoggerFactory.getLogger(UserTest.class);

	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper mapper;

	@PersistenceContext
	EntityManager entityManager;

	static Integer userId;

	@Test
	public void init() {
		User manager = new User(null, "manager", "ADDRESS THAT SHOULD NOT BE SHOWN");
		userService.create(manager);
		User user = new User(null, "user", "address");
		user.setManager(manager);
		userService.create(user);
		userId = user.getId();
		

	}

	@Test
	public void testSerialize() throws JsonProcessingException {
		entityManager.unwrap(Session.class).getSessionFactory().getCache().evictAllRegions();
		User user = userService.read(userId);
		user.getManager().getId();
		String value = mapper.writeValueAsString(user);
		System.err.println(value);
	}
}
