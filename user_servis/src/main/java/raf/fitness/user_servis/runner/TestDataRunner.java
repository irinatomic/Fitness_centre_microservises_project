package raf.fitness.user_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.*;
import raf.fitness.user_servis.repository.*;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;
    private ManagerRepository managerRepository;
    private ClientRepository clientRepository;

    public TestDataRunner(RoleRepository roleRepository, AdminRepository adminRepository, ManagerRepository managerRepository, ClientRepository clientRepository){
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.clientRepository = clientRepository;
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
        Admin admin = new Admin(adminRole, "admin@email.com", "Admin", "Admin", "admin", "admin123", "01112345");
        admin.setActivated(true);
        adminRepository.save(admin);

        // Manager 1
        Manager manager1 = new Manager(managerRole, "manager_one@email.com", "MName_one", "MSurname_one", "manager1", "password", "0123456789", "Gym_one");
        manager1.setActivated(true);
        managerRepository.save(manager1);

        Manager manager2 = new Manager(managerRole, "manager_two@email.com", "MName_two", "MSurname_two", "manager2", "password", "0123456789", "Gym_two");
        manager2.setActivated(true);
        managerRepository.save(manager2);

        Manager manager3 = new Manager(managerRole, "manager_three@email.com", "MName_three", "MSurname_three", "manager3", "password", "0123456789", "Gym_three");
        manager3.setActivated(true);
        managerRepository.save(manager3);

        // Client 1
        Client client1 = new Client(clientRole, "client_one@email.com", "CName_one", "CSurname_one", "client1", "password", "0123456789", "2023-1");
        client1.setActivated(true);
        clientRepository.save(client1);

        // Client 2
        Client client2 = new Client(clientRole, "client_two@email.com", "CName_two", "CSurname_two", "client2", "password", "0123456789", "2023-2");
        //client2.setActivated(true);
        clientRepository.save(client2);
    }
}

