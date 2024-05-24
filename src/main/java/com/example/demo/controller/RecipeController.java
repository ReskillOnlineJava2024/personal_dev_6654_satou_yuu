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
			@RequestParam(name="creater", defaultValue="")String creater,
			Model model) {
		List<Category> categoryList=categoryRepository.findAll();
		List<Recipe> recipeList=null;
		
		if(categoryId!=null) {
			recipeList=recipeRepository.findByCategoryId(categoryId);
		}else if(keyword.length()>0){
			recipeList=recipeRepository.findByNameContaining(keyword);
		}else if(creater.length()>0){
			recipeList=recipeRepository.findByUserName(creater);
		}else {
			recipeList=recipeRepository.findAll();
		}
		
		model.addAttribute("categories",categoryList);
		model.addAttribute("recipes",recipeList);
		model.addAttribute("name",account.getName());
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
		
		String error="";
		Recipe recipe=new Recipe(categoryId,account.getName(),name,material,content);
		model.addAttribute("name",account.getName());
		if(name.length()==0||material.length()==0||content.length()==0) {
			check=false;
			error="すべて入力して下さい";
		}else if(name.length()>20||material.length()>200||content.length()>500) {
			check=false;
			error="正しい文字数で入力して下さい";
		}
		
		if(check==false) {
			model.addAttribute("error",error);
			return "createRecipe";
		}else {
			recipeRepository.save(recipe);
			return "redirect:/recipes";
		}	
	}
	//個人レシピの確認
		@GetMapping("/recipes/parsonal")
		public String personal(Model model) {
			List<Recipe> recipeList=recipeRepository.findByUserName(account.getName());
			model.addAttribute("recipes",recipeList);
			model.addAttribute("name",account.getName());
			return "parsonalRecipes";
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
		Recipe newrecipe=new Recipe(id,categoryId,account.getName(),name,material,content);
		boolean check=true;
		String error="";
		model.addAttribute("name",account.getName());
		if(name.length()==0||material.length()==0||content.length()==0) {
			check=false;
			error="すべて入力して下さい";
		}else if(name.length()>20||material.length()>200||content.length()>500) {
			check=false;
			error="正しい文字数で入力して下さい";
		}
		
		if(check==false) {
			Recipe recipe=recipeRepository.findById(id).orElse(null);
			model.addAttribute("recipe",recipe);
			model.addAttribute("error",error);
			return "editRecipes";
		}else {
			recipeRepository.save(newrecipe);
			List<Recipe> recipeList=recipeRepository.findByUserName(account.getName());
			model.addAttribute("recipes",recipeList);
			Recipe recipe=recipeRepository.findById(id).orElse(null);
			model.addAttribute("recipe",recipe);
			model.addAttribute("message","編集を送信しました");
			return "parsonalRecipes";
		}	
	}
	
	//削除
	@PostMapping("/recipes/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {
		
		recipeRepository.deleteById(id);
		List<Recipe> recipeList=recipeRepository.findByUserName(account.getName());
		model.addAttribute("recipes",recipeList);
		model.addAttribute("name",account.getName());
		model.addAttribute("message","削除しました");
		return "parsonalRecipes";
		
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
	
	//コメントフォーム
	@GetMapping("/recipe/{id}/comment")
	public String review(
			@PathVariable("id") Integer id,
			Model model) {
		List<Category> categoryList=categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		Recipe recipe=recipeRepository.findById(id).get();
		model.addAttribute("name",account.getName());
		model.addAttribute("recipe",recipe);
		return "review";
		
	}
	//コメントの送信
		@PostMapping("/recipe/{id}/comment")
		public String comment(
				@PathVariable("id") Integer id,
				@RequestParam(name="name", defaultValue="")String name,
				@RequestParam(name="comment", defaultValue="")String comment,
				Model model) {
			if(id==0||name.length()==0||comment.length()==0) {
				model.addAttribute("error","コメント：すべて入力して下さい");
			}else if(name.length()>20||comment.length()>500) {
				model.addAttribute("error","コメント：正しい文字数で入力して下さい");
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
