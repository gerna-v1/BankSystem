package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.TestData;
import com.gerna_v1.banksystem.models.DTOs.AdminDTO;
import com.gerna_v1.banksystem.models.DTOs.AdminRegisterDTO;
import com.gerna_v1.banksystem.models.entities.AdminEntity;
import com.gerna_v1.banksystem.repositories.AdminRepository;
import com.gerna_v1.banksystem.services.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserMapper adminMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAdmin() {
        AdminRegisterDTO adminRegister = TestData.testAdminRegister();
        AdminEntity adminEntity = TestData.testAdminEntity();
        AdminDTO adminDTO = TestData.testAdminDTO();

        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);
        when(adminMapper.adminRegisterToAdminEntity(any(AdminRegisterDTO.class))).thenReturn(adminEntity);
        when(adminMapper.adminEntityToAdminDTO(any(AdminEntity.class))).thenReturn(adminDTO);

        AdminDTO result = adminService.createAdmin(adminRegister);

        assertEquals(adminDTO, result);
    }

    @Test
    public void testGetAdminByUsername() {
        String username = TestData.testAdminRegister().getUsername();
        AdminEntity adminEntity = TestData.testAdminEntity();
        AdminDTO adminDTO = TestData.testAdminDTO();

        when(adminRepository.findByUsername(anyString())).thenReturn(Optional.of(adminEntity));
        when(adminMapper.adminEntityToAdminDTO(any(AdminEntity.class))).thenReturn(adminDTO);

        Optional<AdminDTO> result = adminService.getAdminByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(adminDTO, result.get());
    }

    @Test
    public void testGetAdminById() {
        String id = TestData.testAdminEntity().getId();
        AdminEntity adminEntity = TestData.testAdminEntity();
        AdminDTO adminDTO = TestData.testAdminDTO();

        when(adminRepository.findById(anyString())).thenReturn(Optional.of(adminEntity));
        when(adminMapper.adminEntityToAdminDTO(any(AdminEntity.class))).thenReturn(adminDTO);

        Optional<AdminDTO> result = adminService.getAdminById(id);

        assertTrue(result.isPresent());
        assertEquals(adminDTO, result.get());
    }

    @Test
    public void testGetAdminByEmail() {
        String email = TestData.testAdminRegister().getEmail();
        AdminEntity adminEntity = TestData.testAdminEntity();
        AdminDTO adminDTO = TestData.testAdminDTO();

        when(adminRepository.findByEmail(anyString())).thenReturn(Optional.of(adminEntity));
        when(adminMapper.adminEntityToAdminDTO(any(AdminEntity.class))).thenReturn(adminDTO);

        Optional<AdminDTO> result = adminService.getAdminByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(adminDTO, result.get());
    }
}