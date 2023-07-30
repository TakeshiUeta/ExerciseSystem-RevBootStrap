package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.MResult;

@Mapper
public interface ResultMapper {
	// 結果1件取得
	public MResult findById(String id);

	// 結果全件表示
	public List<MResult> findAll();

	// ユーザーIdだけ紐付いている結果表示
	public List<MResult> findbyUserIdOnly(String id);

	// 結果を挿入する
	public void insertOne(@Param("resultID") String resultID, @Param("userID") String userID,
			@Param("resultCal") double resultCal, @Param("resultDistance") double resultDistance,
			@Param("resultCount") int resultCount, @Param("resultDate") String resultDate);

	// 結果の最後に登録したものを取得する
	public MResult findMaxResult();

	//結果を1件削除する
	public void deleteOne(String id);
	
	//ユーザと紐付いている結果の日付だけ取得する
	public List<MResult> findResultDay(String userID);
	
	//ユーザと結果の日付が紐付いている結果を取得する
	public List<MResult> findResultOne_day(String userID,String resultDate);
}
