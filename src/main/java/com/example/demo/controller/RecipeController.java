package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Recipe;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController {
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	Account account;
	
	//レシピ一覧の表示
	@GetMapping("/recipes")
	public String index(
			@RequestParam(name="categoryId",defaultValue="")Integer categoryId,
			@RequestParam(name="keyword", defaultValue="")String keyword,
			Model model) {
		List<Category> categoryList=categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		model.addAttribute("name",account.getName());
		List<Recipe> recipeList=null;
		
		if(categoryId!=null) {
			recipeList=recipeRepository.findByCategoryId(categoryId);
		}else if(keyword.length()>0){
			recipeList=recipeRepository.findByNameContaining(keyword);
		}else {
			recipeList=recipeRepository.findAll();
		}
		model.addAttribute("recipes",recipeList);
		return "recipes";
	}
	
	//新規作成
	@GetMapping("/recipes/add")
	public String add() {
		return "createRecipe";
	}
	
	//作製したレシピの送信
	@PostMapping("/recipes/add")
	public String create(
			@RequestParam(name="categoryId", defaultValue="")Integer categoryId,
			@RequestParam(name="name", defaultValue="")String name,
			@RequestParam(name="material", defaultValue="")String material,
			@RequestParam(name="content", defaultValue="")String content,
			Model model
			) {
		
		Recipe recipe=new Recipe(categoryId,account.getId(),name,material,content);
		recipeRepository.save(recipe);
		return "redirect:/recipes";
	}
	
	//編集
	@GetMapping("/recipes/{id}/edit")
	public String edit(
			@PathVariable("id")Integer id,
			Model model) {
		List<Category> categoryList=categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		Recipe recipe=recipeRepository.findById(id).get();
		model.addAttribute("recipe",recipe);
		return "editRecipes";
	}
	
	//編集の送信
	@PostMapping("/recipes/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name="categoryId", defaultValue="")Integer categoryId,
			@RequestParam(name="name", defaultValue="")String name,
			@RequestParam(name="material", defaultValue="")String material,
			@RequestParam(name="content", defaultValue="")String content,
			Model model) {
		Recipe recipe=new Recipe(id,categoryId,account.getId(),name,material,content);
		recipeRepository.save(recipe);
		
		return "redirect:/recipes";
	}
	
	//削除
	@PostMapping("/recipes/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {
		recipeRepository.deleteById(id);
		return "redirect:/recipes";
	}
	
	//詳細の表示
	@GetMapping("/recipe/detail/{id}")
	public String detail(
			@PathVariable("id") Integer id,
			Model model) {
		Recipe recipe=recipeRepository.findById(id).orElse(null);
		model.addAttribute("recipe",recipe);
		
		return "showRecipe";
	}

}
