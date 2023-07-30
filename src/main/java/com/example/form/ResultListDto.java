package com.example.form;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResultListDto {
	List<Result> resultList;
	public ResultListDto() {}
	public ResultListDto(List<Result> resultList) {
		this.resultList = resultList;
	}
	
}
