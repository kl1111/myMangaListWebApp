package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.User;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.UsersDAO;

public class UsersDaoTest {

	private static UsersDAO usersDao;
	private static TestHelper helper;
	
	@BeforeClass
	public static  void beforeClass(){
		usersDao = new UsersDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
	}
	
	@Before
	public void setUp() {
		helper.dropUsersSequence();
		helper.dropUsersTable();
		helper.createUsersTable();
		helper.createUsersSequence();
	}

	@Test
	public void test_ListUsers_CheckEmptyDatabase() {
		// ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 0;
		// ACT
		usersDao.removeUser("potatoes");
		usersDao.removeUser("tomatoes");
		usersDao.removeUser("daikon");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_ListUsers_RetrieveCorrectUsers() {
		// arrange
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		// act
		userList = usersDao.listUsers();
		// assert
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_ListUsers_ReturnsNull() {
		// arrange
		List<User> userList = new ArrayList<User>();
		// act
		helper.dropUsersTable();
		userList = usersDao.listUsers();
		// assert
		assertNull(userList);
	}

	@Test
	public void test_findByUsername_RetrievesTomatoes() {
		// ARRANGE
		User user;
		int expectedID = 22;
		String expectedUsername = "tomatoes";
		String expectedPassword = "vegetables";
		String expectedFirstName = "Lewis-Chan";
		String expectedLastName = "Luong";
		String expectedEmail = "lewis@luong";
		// ACT
		user = usersDao.findByUsername("tomatoes");
		// ASSERT
		assertEquals(expectedID, user.getUserID());
		assertEquals(expectedUsername, user.getUsername());
		assertEquals(expectedPassword, user.getPassword());
		assertEquals(expectedFirstName, user.getFirstName());
		assertEquals(expectedLastName, user.getLastName());
		assertEquals(expectedEmail, user.getEmail());
	}
	
	@Test
	public void test_findByUsername_ReturnsNullWithInvalidUsername(){
		User user;
		// ACT
		user = usersDao.findByUsername("Alien");
		// ASSERT
		assertNull(user);
	}

	@Test
	public void test_findByUsername_returnsNull() {
		// arrange
		User user;
		// act
		helper.dropUsersTable();
		user=usersDao.findByUsername("karen");
		// assert
		assertNull(user);
	}
	
	@Test
	public void test_findByEmail_RetrievesTomatoes() {
		// ARRANGE
		User user;
		int expectedID = 22;
		String expectedUsername = "tomatoes";
		String expectedPassword = "vegetables";
		String expectedFirstName = "Lewis-Chan";
		String expectedLastName = "Luong";
		String expectedEmail = "lewis@luong";
		// ACT
		user = usersDao.findByEmail("lewis@luong");
		// ASSERT
		assertEquals(expectedID, user.getUserID());
		assertEquals(expectedUsername, user.getUsername());
		assertEquals(expectedPassword, user.getPassword());
		assertEquals(expectedFirstName, user.getFirstName());
		assertEquals(expectedLastName, user.getLastName());
		assertEquals(expectedEmail, user.getEmail());
	}
	
	@Test
	public void test_findByEmail_ReturnsNullWithInvalidUsername(){
		User user;
		// ACT
		user = usersDao.findByEmail("lol@fdm");
		// ASSERT
		assertNull(user);
	}

	@Test
	public void test_findByEmail_returnsNull() {
		// arrange
		User user;
		// act
		helper.dropUsersTable();
		user=usersDao.findByEmail("anon@fdm");
		// assert
		assertNull(user);
	}

	
	@Test
	public void test_removeUser_DeletesDaikon(){
		//ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 2;
		//ACT
		usersDao.removeUser("daikon");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
		
	}

	@Test
	public void test_removeUser_InvalidUserHasNoEffectOnArraySize(){
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		//ACT
		usersDao.removeUser("Alien");
		userList = usersDao.listUsers();
		// ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_addUser_PineappleIncreasesArraySize(){
		//arrange
		List<User> userList = new ArrayList<User>();
		int expected = 4;
		User user = new User();
		user.setUsername("pineapple-kun");
		user.setFirstName("Max");
		user.setLastName("Zhong");
		user.setEmail("max@zhong");
		user.setPassword("fruit");
		//ACT
		usersDao.addUsers(user);
		userList = usersDao.listUsers();
		//ASSERT
		assertEquals(expected, userList.size());
	}
	
	@Test
	public void test_addUser_DupeUserPotatoesLeavesArraySizeUnchanged(){
		//ARRANGE
		List<User> userList = new ArrayList<User>();
		int expected = 3;
		User user = new User();
		user.setUsername("potatoes");
		//ACT
		usersDao.addUsers(user);
		userList = usersDao.listUsers();
		//ASSERT
		assertEquals(expected, userList.size());
	}

	@Test
	public void test_updateUser_UpdateDetailsOfPotatoes(){
		//ARRANGE
		User user = new User();
		user.setUsername("potatoes");
		user.setFirstName("Karen");
		user.setLastName("Luong");
		user.setEmail("karen@luong");
		user.setPassword("fruit");
		//ACT
		usersDao.updateUser(user);
		user = usersDao.findByUsername("potatoes");
		//ASSERT
		assertEquals(21, user.getUserID());
		assertEquals("potatoes", user.getUsername());
		assertEquals("Karen", user.getFirstName());
		assertEquals("Luong", user.getLastName());
		assertEquals("karen@luong", user.getEmail());
		assertEquals("fruit", user.getPassword());
	}

	@Test
	public void test_updateUser_UpdateInvalidUser(){
		User user = new User();
		user.setUsername("Alien");
		user.setFirstName("Karen");
		user.setLastName("Luong");
		user.setEmail("karen@luong");
		user.setPassword("fruit");
		//ACT
		usersDao.updateUser(user);
		user = usersDao.findByUsername("Alien");
		//ASSERT
		assertNull(user);
	}
	
	@AfterClass
	public static void closeConnection(){
		Connections.CloseConnection();
	}
}
