package com.example.shamanApi.repository;

import com.example.shamanApi.model.Restaurant;
import com.example.shamanApi.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
}
