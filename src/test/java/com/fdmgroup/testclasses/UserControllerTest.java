package com.fdmgroup.testclasses;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.UserController;
import com.fdmgroup.models.daos.Connections;
import static org.junit.Assert.*;

public class UserControllerTest {

	private static UserController controller;
	private static TestHelper helper;
	
	@BeforeClass
	public static void beforeClass() {
		controller = new UserController();
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
	public void test_checkUserDetails_ifDetailsAreCorrect() {
		// ARRANGE
		int expected = 0;
		// act
		int actual = controller.checkUserDetails("daikon", "vegetables");
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_checkUserDetails_ifPasswordIsIncorrect() {
		// ARRANGE
		int expected = 1;
		// act
		int actual = controller.checkUserDetails("daikon", "radish");
		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void test_checkUserDetails_ifUsernameIsInvalid() {
		// ARRANGE
		int expected = 2;
		// act
		int actual = controller.checkUserDetails("daikons", "vegetables");
		// assert
		assertEquals(expected, actual);
	}
	
	@AfterClass
	public static void closeConnection() {
		Connections.CloseConnection();
	}

}
