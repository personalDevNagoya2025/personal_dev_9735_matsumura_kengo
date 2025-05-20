package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class Taskcontroller {
	private static final boolean DELETED = true;
	private static final boolean NOT_DELETED = false;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	Account account;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	//タスク一覧を表示
	@GetMapping("/tasks")
	public String index(
			@RequestParam(value = "keyword" , defaultValue="") String keyword,
			Model model) {
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		
		List<Task> taskList = null;
		if(keyword.length() > 0) {
			taskList = taskRepository.findByTitleContaining(keyword);
		}else {
			taskList =  taskRepository.findAllByDeleted(false);
		}
		
		model.addAttribute("keyword",keyword);
		model.addAttribute("tasks", taskList);
		
		
		return "tasks";
	}
	

	@GetMapping("/tasks/add")
	public String taskAddDisp(
			Model model) {
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories",categoryList);
		
		return "addTask";
	}

	@PostMapping("/tasks/add")
	public String taskAdd(
			@RequestParam("title") String title,
			@RequestParam("closingdate") Date closingdate,
			@RequestParam("memo") String memo,
			@RequestParam("categoriesId") Integer categoriesId,
			Model model) {
		
		if(title.length() == 0 ) {
			model.addAttribute("error", "タイトルを入力してください");
			return "addTask";
		}
		
		Task task = new Task(title, closingdate, memo, categoriesId);
		taskRepository.save(task);

		return "redirect:/tasks";
	}

	@GetMapping("/tasks/{id}/update")
	public String taskEditDisp(
			@PathVariable("id") Integer id,
			Model model) {

		Task task = taskRepository.findById(id).get();
		model.addAttribute("task", task);
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories",categoryList);

		return "editTask";
	}

	@PostMapping("/tasks/{id}/update")
	public String taskEdit(
			@PathVariable("id") Integer id,
			@RequestParam("title") String title,
			@RequestParam("closingdate") Date closingdate,
			@RequestParam("memo") String memo,
			@RequestParam("categoriesId") Integer categoriesId
			) {

		Task task = taskRepository.findById(id).get();
		task.setTitle(title);
		task.setClosingdate(closingdate);
		task.setMemo(memo);
		task.setCategoriesId(categoriesId);
		taskRepository.save(task);

		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/delete")
	public String taskDelete(@PathVariable("id") Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks";
	}

	@PostMapping("tasks/{id}/comp")
	public String comp(
			@PathVariable("id") Integer id) {

		Optional<Task> result = taskRepository.findByIdAndDeleted(id, NOT_DELETED);
		if (result.isPresent()) {
			Task task = result.get();
			task.setDeleted(DELETED);
			taskRepository.save(task);
		}
		return "redirect:/tasks";
	}

	@GetMapping("tasks/his")
	public String his(
			Model model) {
		List<Task> taskList = taskRepository.findAllByDeleted(true);
		model.addAttribute("tasks", taskList);

		return "taskshis";
	}

	@PostMapping("tasks/his")
	public String his() {
		return "redirect:/tasks";
	}

	@PostMapping("tasks/{id}/ret")
	public String ret(
			@PathVariable("id") Integer id) {

		Optional<Task> result = taskRepository.findByIdAndDeleted(id, DELETED);
		if (result.isPresent()) {
			Task task = result.get();
			task.setDeleted(NOT_DELETED);
			taskRepository.save(task);
		}
		return "redirect:/tasks/his";
	}
}
