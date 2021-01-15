package org.pih.warehouse.core

import grails.test.*
import org.junit.Ignore
import org.pih.warehouse.core.Role
import org.pih.warehouse.core.User

// import AuthService

import testutils.DbHelper;

@Ignore
class UserServiceIntegrationTests extends GroovyTestCase{

	def userService 
	
	void setUp() {
		super.setUp()
	}
	
	
	
	void test_getAllAdminUsers() { 		
		// Create a new inactive user who has role ROLE_ADMIN
		User adminUser = DbHelper.createAdmin("Firstson", "Miranda III", "fmiranda@pih.org", "fmiranda", "password", false)
		assertNotNull adminUser
		assertFalse adminUser.active
		
		def roleAdmin = Role.findByRoleType(RoleType.ROLE_ADMIN)
		assertNotNull "Should exist", roleAdmin 
		
		def allUsers = User.list()
		def allAdmins = allUsers.findAll { it.roles.contains(roleAdmin) } 
		assertEquals "Should be 2 users with ROLE_ADMIN", 2, allAdmins.size()
		
		// Should return active admins
		def activeAdmins = userService.getAllAdminUsers()
		assertFalse "Should not contain inactive users with ROLE_ADMIN", activeAdmins.contains(adminUser)
		assertEquals 1, activeAdmins.size()
		activeAdmins.each { admin -> 			
			assertTrue "Should have ROLE_ADMIN", admin.roles.contains(roleAdmin)
			assertTrue "Should be active", admin.active
		}
		
		
		
		
	}
}
