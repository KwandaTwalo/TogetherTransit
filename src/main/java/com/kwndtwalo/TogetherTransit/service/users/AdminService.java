package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.repository.users.IAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    private IAdminRepository adminRepository;

    @Autowired
    public AdminService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /*
     * Create or return existing Admin.
     * Business rule:
     * Same first name + last name = same Admin (duplicate prevention).
     */
    @Override
    public Admin create(Admin admin) {
        if (admin == null) {
            return null;
        }

        return adminRepository
                .findByFirstNameAndLastName(
                        admin.getFirstName(),
                        admin.getLastName()
                )
                .orElseGet(() -> adminRepository.save(admin));
    }

    @Override
    public Admin read(Long Id) {
        return adminRepository.findById(Id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (admin == null || admin.getUserId() == null) {
            return null;
        }
        if (!adminRepository.existsById(admin.getUserId())) {
            return null;
        }
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
       if (!adminRepository.existsById(Id)) {
           return false;
       }
       adminRepository.deleteById(Id);
       return true;
    }
}
