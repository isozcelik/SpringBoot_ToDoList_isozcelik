package com.isozcelik.data.repository;

import com.isozcelik.data.entity.ToDoListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToDoListRepository extends JpaRepository<ToDoListEntity,Long> {
}
