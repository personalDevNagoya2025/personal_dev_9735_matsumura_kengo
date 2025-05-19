package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer>{
	
List<Task> findAllByDeleted(boolean notDeleted);	
Optional<Task> findByIdAndDeleted(Integer id, boolean deleted);
Optional<Task> findByIdAndNotDeleted(Integer id, boolean deleted);

}
