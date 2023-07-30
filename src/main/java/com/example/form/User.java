package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {
	/** 会員ID */
	@NotBlank
	@Size(min = 4, max = 6)
	private String userID;

	/** 会員名 */
	@NotBlank
	@Size(min = 1, max = 32)
	private String userName;

	/** 電話番号 */
	@NotBlank
	@Size(min = 10, max = 13)
	private String userTelno;

	/** 郵便番号 */
	@NotBlank
	@Size(min = 8, max = 8)
	private String userPostcode;

	/** 住所 */
	@NotBlank
	@Size(min = 10, max = 40)
	private String userAddress;

	/** パスワード */
	@NotBlank
	@Size(min = 6, max = 6)
	@Pattern(regexp = "[0-9a-zA-Z]+$")
	private String userPassword;

	// コンストラクタ
	public User() {}
	public User(String userID, String userName, String userTelno, String userPostcode, String userAddress,
			String userPassword) {
		this.userID = userID;
		this.userName = userName;
		this.userTelno = userTelno;
		this.userPostcode = userPostcode;
		this.userAddress = userAddress;
		this.userPassword = userPassword;
	}
}
