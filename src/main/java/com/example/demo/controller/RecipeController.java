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
import com.example.demo.entity.Review;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
@Controller
public class RecipeController {
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ReviewRepository reviewRepository;
	
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
	public String add(Model model) {
		model.addAttribute("name",account.getName());
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
		boolean check=true;
		
		Recipe recipe=new Recipe(categoryId,account.getName(),name,material,content);
		model.addAttribute("name",account.getName());
		if(name.length()==0) {
			check=false;
		}
		if(material.length()==0) {
			check=false;
		}
		if(content.length()==0) {
			check=false;
		}
		
		if(check==false) {
			model.addAttribute("error","全ての項目を入力して下さい");
			return "createRecipe";
		}else {
			recipeRepository.save(recipe);
			return "redirect:/recipes";
		}	
	}
	
	//編集
	@GetMapping("/recipes/{id}/edit")
	public String edit(
			@PathVariable("id")Integer id,
			Model model) {
		List<Category> categoryList=categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		Recipe recipe=recipeRepository.findById(id).get();
		model.addAttribute("name",account.getName());
		if(account.getName().equals(recipe.getUserName())) {
			model.addAttribute("recipe",recipe);
			return "editRecipes";
		}else {
			List<Recipe> recipeList=recipeRepository.findAll();
			model.addAttribute("recipes",recipeList);
			model.addAttribute("error","エラー：編集権限はありません");
			return "recipes";
		}
		
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
		Recipe recipe=new Recipe(id,categoryId,account.getName(),name,material,content);
		recipeRepository.save(recipe);
		model.addAttribute("name",account.getName());
		List<Recipe> recipeList=recipeRepository.findAll();
		model.addAttribute("recipes",recipeList);

		model.addAttribute("message","編集を送信しました");
		return "recipes";
	}
	
	//削除
	@PostMapping("/recipes/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {
		model.addAttribute("name",account.getName());
		Recipe recipe=recipeRepository.findById(id).get();
		model.addAttribute("name",account.getName());
		if(account.getName().equals(recipe.getUserName())) {
			model.addAttribute("message","削除しました");
			recipeRepository.deleteById(id);
		}else {
			model.addAttribute("error","エラー：権限はありません");
		}
		List<Recipe> recipeList=recipeRepository.findAll();
		model.addAttribute("recipes",recipeList);
		return "recipes";
		
	}
	
	//詳細の表示
	@GetMapping("/recipe/detail/{id}")
	public String detail(
			@PathVariable("id") Integer id,
			Model model) {
		Recipe recipe=recipeRepository.findById(id).orElse(null);
		List<Review> reviews=reviewRepository.findByRecipeId(id);
		model.addAttribute("recipe",recipe);
		model.addAttribute("reviews",reviews);
		model.addAttribute("name",account.getName());
		
		return "showRecipe";
	}
	
	//コメントの送信
		@PostMapping("/recipe/comment")
		public String comment(
				@RequestParam(name="id", defaultValue="0") Integer id,
				@RequestParam(name="name", defaultValue="")String name,
				@RequestParam(name="comment", defaultValue="")String comment,
				Model model) {
			if(id==0||name.length()==0||comment.length()==0) {
				model.addAttribute("error","コメント：すべて入力して下さい");
			}else {
				model.addAttribute("message","コメント：送信されました");
				Review review=new Review(id,name,comment);
				reviewRepository.save(review);
			}
			List<Recipe> recipeList=recipeRepository.findAll();
			model.addAttribute("recipes",recipeList);
			model.addAttribute("name",account.getName());
			return "recipes";
		}
	

}
