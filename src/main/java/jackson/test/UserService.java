package jackson.test;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	Integer userId;
	
	
	
	public void create(User user){
		entityManager.persist(user);
		entityManager.flush();
	}
	
	public void update(User user){
		entityManager.merge(user);
	}
	
	public User read(int id){
		return entityManager.find(User.class, id);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> list(){
		return entityManager.createQuery("from User").getResultList();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
}
