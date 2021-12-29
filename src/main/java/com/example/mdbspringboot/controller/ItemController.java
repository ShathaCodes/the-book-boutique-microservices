package com.example.mdbspringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mdbspringboot.model.GroceryItem;
import com.example.mdbspringboot.repository.ItemRepository;

@RestController
public class ItemController {

	@Autowired
	ItemRepository groceryItemRepo;

	List<GroceryItem> itemList = new ArrayList<GroceryItem>();

	@ResponseBody
	@GetMapping("/")
	public String home() {
		String html = "";
		html += "<ul>";
		html += " <li><a href='/create'>Create an item</a></li>";
		html += " <li><a href='/update'>Update an Item</a></li>";
		html += " <li><a href='/delete'>Delete Item</a></li>";
		html += "</ul>";
		return html;
	}

	@ResponseBody
	@RequestMapping("/create")
	public String create() {
		GroceryItem item = new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks");

		groceryItemRepo.save(item);

		return "Inserted: " + item;
	}

	@ResponseBody
	@RequestMapping("/update")
	public String update() {
		String id = "Dried Red Chilli";
		String newCategory = "munchies";

		GroceryItem item = groceryItemRepo.findById(id).get();

		item.setCategory(newCategory);

		GroceryItem itemUpdated = groceryItemRepo.save(item);

		return "Updates: " + itemUpdated;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public String delete() {
		String id = "Dried Red Chilli";

		groceryItemRepo.deleteById(id);

		return "Deleted";
	}
}
