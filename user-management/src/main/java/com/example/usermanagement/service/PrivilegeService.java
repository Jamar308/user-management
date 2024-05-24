package com.example.usermanagement.service;

import com.example.usermanagement.dto.PrivilegeRequest;
import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.repositories.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;


    public Privilege createPrivilege(PrivilegeRequest privilegeRequest) {
        Privilege privilege = new Privilege(privilegeRequest);
        return privilegeRepository.save(privilege);
    }

    public List<Privilege> getAllPrivileges() {

        return privilegeRepository.findAll();
    }

    public Optional<Privilege> getPrivilegeById(Long id) {
        return privilegeRepository.findById(id);
    }

    public Privilege updatePrivilege(Long id, PrivilegeRequest privilegeRequest) {
        return privilegeRepository.findById(id)
                .map(privilege -> {
                    privilege.setPrivilegeName(privilegeRequest.getPrivilegeName());
                    privilege.setCode(privilegeRequest.getCode());
                    privilege.setPrivilegeDescription(privilegeRequest.getPrivilegeDescription());
                    privilege.setCreatedDate(privilegeRequest.getCreatedDate());
                    privilege.setUpdatedDate(LocalDateTime.now());
                    return privilegeRepository.save(privilege);
                })
                .orElse(null);
    }

    public void deletePrivilege(Long id) {
        privilegeRepository.deleteById(id);
    }

    public Privilege findByName(String code) {
        return privilegeRepository.findByCode(code);
    }

    public void updatePrivilege(Privilege existingPrivilege) {
    }
}
