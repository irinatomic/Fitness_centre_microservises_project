package raf.fitness.user_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.Admin;
import raf.fitness.user_servis.domain.Role;
import raf.fitness.user_servis.repository.AdminRepository;
import raf.fitness.user_servis.repository.RoleRepository;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;

    public TestDataRunner(RoleRepository roleRepository, AdminRepository adminRepository) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {

         // Roles
         Role adminRole = new Role("ADMIN");
         Role managerRole = new Role("MANAGER");
         Role clientRole = new Role("CLIENT");
         roleRepository.save(adminRole);
         roleRepository.save(managerRole);
         roleRepository.save(clientRole);

         // Admin
        Admin admin = new Admin("admin@email.com", "Admin", "Admin", "admin", "admin123", "01112345", adminRole);
        admin.setActivated(true);
        adminRepository.save(admin);

    }
}

