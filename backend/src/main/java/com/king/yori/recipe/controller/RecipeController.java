package com.king.yori.recipe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:3000","http://192.168.0.10:3000" })
@RestController
@RequestMapping("/recipe")
public class RecipeController {
	
	
}
