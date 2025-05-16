package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@Controller
public class Taskcontroller {

	@Autowired
	TaskRepository taskRepository;
	
	//タスク一覧を表示
	@GetMapping("/tasks")
	public String index(Model model) {
		//
		List<Task> taskList = taskRepository.findAll();
		model.addAttribute("tasks", taskList);
		model.addAttribute("tasks",taskList);
		
		
		return "tasks";
	}
	
	@GetMapping("/tasks/add")
	public String taskAddDisp() {
		return "addTask";
	}
	
	@PostMapping("/tasks/add")
	public String taskAdd(
			@RequestParam("title") String title,
			Model model
			) {
		Task task = new Task(title);
		taskRepository.save(task);
		
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks/{id}/update")
	public String taskEditDisp(
			@PathVariable("id") Integer id,
			Model model
			) {
		
		Task task = taskRepository.findById(id).get();
		model.addAttribute("task",task);
		
		return "editTask";
	}
	
	@PostMapping("/tasks/{id}/update")
	public String taskEdit(
			@PathVariable("id") Integer id,
			@RequestParam("title") String title
			) {
		
		Task task = taskRepository.findById(id).get();
		task.setTitle(title);
		taskRepository.save(task);
		
		return "redirect:/tasks";
	}
	
	@PostMapping("/tasks/{id}/delete")
	public String taskDelete(@PathVariable("id") Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks";
	}
}
