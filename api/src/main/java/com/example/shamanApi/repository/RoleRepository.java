package com.example.shamanApi.repository;

import com.example.shamanApi.model.Restaurant;
import com.example.shamanApi.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    @NonNull
    Role findByName(@NonNull String name);
}
