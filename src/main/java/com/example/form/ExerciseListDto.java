package com.example.form;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ExerciseListDto {
	private List<Exercise> exerciseList;
	
	public ExerciseListDto() {}
	
	public ExerciseListDto(List<Exercise> exerciseList){
		this.exerciseList = exerciseList;
	}
}
