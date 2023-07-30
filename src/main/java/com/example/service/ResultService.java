package com.example.service;

import java.util.List;


import com.example.domain.user.model.MResult;

public interface ResultService {
	// 結果1件取得
	public MResult findByResultId(String resultId);

	// 結果全件表示
	public List<MResult> findResultAll();

	// ユーザーIdだけ紐付いている結果表示
	public List<MResult> findResultbyUserOnly(String userId);

	// 結果を挿入する
	public void insertOne(MResult mResult);

	// 結果のMaxIdを取得する
	public MResult findMaxResultId();

	//結果を1件削除する
	public void deleteOne(String resultId);
	
	//ユーザと紐付いている結果の日付だけ取得する
	public List<MResult> findResultDay(String userID);
	
	//ユーザと結果の日付が紐付いている結果を取得する
	public List<MResult> findResultOne_day(String userID,String resultDate);
}
