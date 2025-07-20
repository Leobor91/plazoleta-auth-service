package com.pragma.plazadecomidas.authservice.infrastructure.configuration;

import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(IRoleRepository roleRepository) {
        return args -> {
            Map<String, String> roles = Map.of(
                    "ADMINISTRADOR", "Gestiona toda la plataforma",
                    "PROPIETARIO", "Administra su propio restaurante",
                    "EMPLEADO", "Atiende pedidos y gestiona operaciones",
                    "CLIENTE", "Realiza pedidos y consulta menús"
            );
            for (Map.Entry<String, String> entry : roles.entrySet()) {
                String roleName = entry.getKey();
                String description = entry.getValue();
                Optional<RoleEntity> roleExisying = roleRepository.findByName(roleName);
                if (roleExisying.isEmpty()){
                    roleRepository.save(RoleEntity.builder()
                                    .name(roleName)
                                    .description(description)
                            .build());
                }


            }
        };
    }
}

