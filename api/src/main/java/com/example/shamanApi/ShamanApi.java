package com.example.shamanApi;

import com.example.shamanApi.data.Seed;
import com.example.shamanApi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShamanApi implements CommandLineRunner {

	private UserRepository userRepository;
	private RestaurantRepository restaurantRepository;
	private CartItemRepository cartItemRepository;
	private DishRepository dishRepository;
	private DiscountRepository discountRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public ShamanApi(UserRepository userRepository,
					 RestaurantRepository restaurantRepository,
					 CartItemRepository cartItemRepository,
					 DishRepository dishRepository,
					 DiscountRepository discountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
		this.cartItemRepository = cartItemRepository;
		this.dishRepository = dishRepository;
		this.discountRepository = discountRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	public static void main(String[] args) {
		SpringApplication.run(ShamanApi.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Seed seed = new Seed(roleRepository, passwordEncoder, userRepository,restaurantRepository,cartItemRepository,dishRepository,discountRepository);
		seed.seedData();
	}
}
