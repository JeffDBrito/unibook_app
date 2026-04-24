package com.unibook.app.config;

import com.unibook.app.model.Permission;
import com.unibook.app.model.Role;
import com.unibook.app.repository.PermissionRepository;
import com.unibook.app.repository.RoleRepository;
import com.unibook.app.repository.UserRepository;
import com.unibook.app.service.AuthorService;
import com.unibook.app.service.BookService;
import com.unibook.app.service.CategoryService;
import com.unibook.app.service.PublisherService;
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
            createRoleIfNotExists(roleRepository, "SUPER_ADMIN"); // Can manage everything
            createRoleIfNotExists(roleRepository, "LIBRARIAN"); // Can view catalog, borrow books, borrow more books than students, borrow for longer periods, borrow restricted materials, manage inventory, process returns, etc.
            createRoleIfNotExists(roleRepository, "TEACHER"); // Can view catalog, borrow books, borrow more books than students, borrow for longer periods, borrow restricted materials
            createRoleIfNotExists(roleRepository, "STUDENT"); // Can view catalog, borrow books
            createRoleIfNotExists(roleRepository, "GUEST"); // Can view catalog

        };
    }

    private void createRoleIfNotExists(RoleRepository repo, String title) {
        Role role = repo.findByTitle(title).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setTitle(title);
            return repo.save(newRole);
        });

        System.out.println("Role '" + role.getTitle() + "' initialized with ID: " + role.getId());

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
            roleService.assignPermissionsByRoleName("SUPER_ADMIN", allPermissions);
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
                List.of(
                    roleRepository.findByTitle("ADMIN")
                        .orElseThrow(() -> new RuntimeException("Admin role not found"))
                        .getId(),                    
                    roleRepository.findByTitle("SUPER_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Super Admin role not found"))
                        .getId()
                )
            );
        };
    }

    @Bean
    @Order(5)
    CommandLineRunner initAuthors(AuthorService authorService) {
        return args -> {
            authorService.createAuthor("F. Scott Fitzgerald", "American novelist and short story writer, widely regarded as one of the greatest American writers of the 20th century.");
            authorService.createAuthor("Harper Lee", "American novelist best known for her 1960 novel To Kill a Mockingbird, which won the Pulitzer Prize.");
            authorService.createAuthor("George Orwell", "English novelist, essayist, journalist and critic, whose work is marked by lucid prose, biting social criticism, opposition to totalitarianism, and outspoken support of democratic socialism.");
        };
    }

    @Bean
    @Order(6)    
    CommandLineRunner initCategories(CategoryService categoryService) {
        return args -> {
            categoryService.createCategory("Fiction", "Literary works invented by the imagination, such as novels or short stories.");
            categoryService.createCategory("Non-Fiction", "Literary works based on facts, real events, and real people, such as biography or history.");
            categoryService.createCategory("Science Fiction", "A genre of speculative fiction that typically deals with imaginative and futuristic concepts such as advanced science and technology, space exploration, time travel, parallel universes, and extraterrestrial life.");
        };
    }

    @Bean
    @Order(7)    
    CommandLineRunner initPublishers(PublisherService publisherService) {
        return args -> {
            publisherService.createPublisher("Scribner", "An American publishing company based in New York City, known for publishing many classic and contemporary works of fiction.");
            publisherService.createPublisher("J.B. Lippincott & Co.", "An American publishing house founded in Philadelphia in 1836, known for publishing many classic works of literature, including Harper Lee's To Kill a Mockingbird.");
            publisherService.createPublisher("Secker & Warburg", "A British publishing company founded in 1935, known for publishing many influential works of literature, including George Orwell's 1984 and Animal Farm.");
        };
    }

    @Bean
    @Order(8)
    CommandLineRunner initBooks(BookService bookService, AuthorService authorService, CategoryService categoryService, PublisherService publisherService) {
        return args -> {
            bookService.createBook("The Great Gatsby", "978-0743273565", "A novel by F. Scott Fitzgerald", 1925, publisherService.findByTitle("Scribner").getId(), List.of(authorService.findByName("F. Scott Fitzgerald").getId(), authorService.findByName("George Orwell").getId()), List.of(categoryService.findByTitle("Fiction").getId(), categoryService.findByTitle("Science Fiction").getId()));
            bookService.createBook("To Kill a Mockingbird", "978-0061120084", "A novel by Harper Lee", 1960, publisherService.findByTitle("J.B. Lippincott & Co.").getId(), List.of(authorService.findByName("Harper Lee").getId()), List.of(categoryService.findByTitle("Fiction").getId()));
            bookService.createBook("1984", "978-0451524935", "A novel by George Orwell", 1949, publisherService.findByTitle("Secker & Warburg").getId(), List.of(authorService.findByName("George Orwell").getId()), List.of(categoryService.findByTitle("Fiction").getId(), categoryService.findByTitle("Science Fiction").getId()));
        };
    }



}
