package com.example.mdbspringboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mdbspringboot.model.GroceryItem;

public interface ItemRepository extends MongoRepository<GroceryItem, String> {
}
