package com.unibook.app.config;

import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.repository.PermissionRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;
import com.unibook.app.service.RoleService;
import com.unibook.app.service.UserService;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DataInitializer {

    @Bean
    @Order(1)
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            createRoleIfNotExists(roleRepository, "ADMIN"); // Can manage everything
            createRoleIfNotExists(roleRepository, "LIBRARIAN"); // Can view catalog, borrow books, borrow more books than students, borrow for longer periods, borrow restricted materials, manage inventory, process returns, etc.
            createRoleIfNotExists(roleRepository, "TEACHER"); // Can view catalog, borrow books, borrow more books than students, borrow for longer periods, borrow restricted materials
            createRoleIfNotExists(roleRepository, "STUDENT"); // Can view catalog, borrow books
            createRoleIfNotExists(roleRepository, "GUEST"); // Can view catalog

        };
    }

    private void createRoleIfNotExists(RoleRepository repo, String title) {
        repo.findByTitle(title).orElseGet(() -> {
            Role role = new Role();
            role.setTitle(title);
            return repo.save(role);
        });
    }

    @Bean
    @Order(2)
    CommandLineRunner initPermissions(PermissionRepository permissionRepository) {
        return args -> {

            // Admin
            createPermissionIfNotExists(permissionRepository,"ADMIN_IMPERSONATE", "Admin", "Impersonate another user");
            createPermissionIfNotExists(permissionRepository,"ADMIN_MANAGE_SETTINGS", "Admin", "Manage system settings");

            // Loan
            createPermissionIfNotExists(permissionRepository, "LOAN_CREATE", "Loan", "Create a new loan");
            createPermissionIfNotExists(permissionRepository, "LOAN_READ", "Loan", "View loan details");
            createPermissionIfNotExists(permissionRepository, "LOAN_RETURN", "Loan", "Return a loan");
            createPermissionIfNotExists(permissionRepository, "LOAN_CANCEL", "Loan", "Cancel a loan");
            createPermissionIfNotExists(permissionRepository, "LOAN_VIEW_ALL", "Loan", "View all loans");

            // Book
            createPermissionIfNotExists(permissionRepository, "BOOK_CREATE", "Book", "Create a new book");
            createPermissionIfNotExists(permissionRepository, "BOOK_READ", "Book", "View book details");
            createPermissionIfNotExists(permissionRepository, "BOOK_UPDATE", "Book", "Update book information");
            createPermissionIfNotExists(permissionRepository, "BOOK_DELETE", "Book", "Delete a book");

            // User
            createPermissionIfNotExists(permissionRepository, "USER_CREATE", "User", "Create a new user");
            createPermissionIfNotExists(permissionRepository, "USER_READ", "User", "View user details");
            createPermissionIfNotExists(permissionRepository, "USER_UPDATE", "User", "Update user information");
            createPermissionIfNotExists(permissionRepository, "USER_DELETE", "User", "Delete a user");
            createPermissionIfNotExists(permissionRepository, "USER_VIEW_ALL", "User", "View all users");
            createPermissionIfNotExists(permissionRepository, "USER_SET_SUPERUSER", "User", "Set user as superuser");

            // Person
            createPermissionIfNotExists(permissionRepository, "PERSON_CREATE", "Person", "Create a new person");
            createPermissionIfNotExists(permissionRepository, "PERSON_READ", "Person", "View person details");
            createPermissionIfNotExists(permissionRepository, "PERSON_UPDATE", "Person", "Update person information");
            createPermissionIfNotExists(permissionRepository, "PERSON_DELETE", "Person", "Delete a person");

            // Author
            createPermissionIfNotExists(permissionRepository, "AUTHOR_CREATE", "Author", "Create a new author");
            createPermissionIfNotExists(permissionRepository, "AUTHOR_READ", "Author", "View author details");
            createPermissionIfNotExists(permissionRepository, "AUTHOR_UPDATE", "Author", "Update author information");
            createPermissionIfNotExists(permissionRepository, "AUTHOR_DELETE", "Author", "Delete an author");
            createPermissionIfNotExists(permissionRepository, "AUTHOR_VIEW_ALL", "Author", "View all authors");

            // Category
            createPermissionIfNotExists(permissionRepository, "CATEGORY_CREATE", "Category", "Create a new category");
            createPermissionIfNotExists(permissionRepository, "CATEGORY_READ", "Category", "View category details");
            createPermissionIfNotExists(permissionRepository, "CATEGORY_UPDATE", "Category", "Update category information");
            createPermissionIfNotExists(permissionRepository, "CATEGORY_DELETE", "Category", "Delete a category");
            createPermissionIfNotExists(permissionRepository, "CATEGORY_VIEW_ALL", "Category", "View all categories");

            // Publisher
            createPermissionIfNotExists(permissionRepository, "PUBLISHER_CREATE", "Publisher", "Create a new publisher");
            createPermissionIfNotExists(permissionRepository, "PUBLISHER_READ", "Publisher", "View publisher details");
            createPermissionIfNotExists(permissionRepository, "PUBLISHER_UPDATE", "Publisher", "Update publisher information");
            createPermissionIfNotExists(permissionRepository, "PUBLISHER_DELETE", "Publisher", "Delete a publisher");
            createPermissionIfNotExists(permissionRepository, "PUBLISHER_VIEW_ALL", "Publisher", "View all publishers");

            // Copy
            createPermissionIfNotExists(permissionRepository, "COPY_CREATE", "Copy", "Create a new copy");
            createPermissionIfNotExists(permissionRepository, "COPY_READ", "Copy", "View copy details");
            createPermissionIfNotExists(permissionRepository, "COPY_UPDATE", "Copy", "Update copy information");
            createPermissionIfNotExists(permissionRepository, "COPY_DELETE", "Copy", "Delete a copy");
            createPermissionIfNotExists(permissionRepository, "COPY_MARK_LOST", "Copy", "Mark copy as lost");
            createPermissionIfNotExists(permissionRepository, "COPY_MARK_DAMAGED", "Copy", "Mark copy as damaged");
            
            // Fine
            createPermissionIfNotExists(permissionRepository, "FINE_CREATE", "Fine", "Create a new fine");
            createPermissionIfNotExists(permissionRepository, "FINE_READ", "Fine", "View fine details");
            createPermissionIfNotExists(permissionRepository, "FINE_PAY", "Fine", "Pay a fine");
            createPermissionIfNotExists(permissionRepository, "FINE_VIEW_ALL", "Fine", "View all fines");

        };
    }

    private void createPermissionIfNotExists(PermissionRepository repo, String title, String group, String description) {
        repo.findByTitle(title).orElseGet(() -> {
            Permission permission = new Permission();
            permission.setTitle(title);
            permission.setGroupName(group);
            permission.setDescription(description);
            return repo.save(permission);
        });
    }

    @Bean
    @Order(3)
    CommandLineRunner initRolePermissions(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RoleService roleService
    ) {
        return args -> {
            List<Permission> allPermissions = permissionRepository.findAll();
            roleService.assignPermissionsByRoleName("ADMIN", allPermissions);
        };
    }

    @Bean
    @Order(4)
    CommandLineRunner initAdminUser(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            userService.createUser(
                "Admin User",
                "admin@admin.com",
                "admin",
                "admin",
                roleRepository.findByTitle("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"))
                    .getId(),
                true
            );
        };
    }
}
