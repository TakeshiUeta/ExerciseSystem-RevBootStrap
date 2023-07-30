package com.example.domain.user.model;

import lombok.Data;

@Data
public class MResult {
	/* 筋トレ結果ID */
	private String resultID;
	/* 会員ID */
	private String userID;
	/* 合計消費カロリー */
	private double resultCal;
	/* 進んだ距離 */
	private double resultDistance;
	/* 筋トレ件数 */
	private int resultCount;
	/* 筋トレ日 */
	private String resultDate;
}
