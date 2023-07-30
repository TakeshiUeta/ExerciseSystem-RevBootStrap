package com.example.controller;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MUser;
import com.example.form.User;
import com.example.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/exerciseUser")
public class UserController2 {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	HttpSession session;
	@Autowired
	ModelMapper modelMapper;

	// ログイン画面に遷移する
	@GetMapping("/MS101UserLogin")
	public String getMS101UserLogin(Model model, @ModelAttribute User user) {
		return "exerciseUser/MS101UserLogin";
	}

	// top画面に遷移する
	@PostMapping("/MS111LetsExercise")
	public String postMS111LetｓExercise(Model model, @ModelAttribute User user) {
		MUser mUser = new MUser();
		String userID = "";
		String password = "";
		try {
			userID = user.getUserID();
			password = user.getUserPassword();
			// DB側のユーザー情報を取得する
			mUser = userService.findUserOne(userID);
			// DB側のパスワードを取得する
			String userPassword = mUser.getUserPassword();
			// IDとパスワードに相違がある場合メッセージを表示させてユーザー画面に遷移する
			if (!password.equals(userPassword)) {
				String msg = "会員IDまたはパスワードが違います";
				model.addAttribute("msg", msg);
				return "exerciseUser/MS101UserLogin";
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg = "会員IDまたはパスワードが違います";
			model.addAttribute("msg", msg);
			return "exerciseUser/MS101UserLogin";
		}
		user = modelMapper.map(mUser, User.class);
		session.setAttribute("user", user);
		return "exerciseUser/MS111LetsExercise";
	}
	
	//更新画面からTOP画面に遷移する
	@GetMapping("/MS111LetsExercise")
	public String getMS111LetsExercise() {
		return "exerciseUser/MS111LetsExercise";
	}
	
	// 会員登録画面に遷移する
	@PostMapping("/MS102UserRegist")
	public String postMS102UserRegist(Model model, @ModelAttribute User user) {
		return "exerciseUser/MS102UserRegist";
	}

	// 会員登録を行う
	@GetMapping("/MS102UserRegist")
	public String getMS102UserRegist(Model model, @Validated User user, BindingResult bindingResult) {
		// IDの最大値を出す
		String id = userService.findMaxIdUser();
		// userにIDをセットする
		user.setUserID(id);
		// バリデーションチェック
		/*
		 * if(bindingResult.hasErrors()) { model.addAttribute("user",user); return
		 * "exerciseUser/MS102UserRegist"; }
		 */
		MUser mUser = modelMapper.map(user, MUser.class);
		// 会員登録
		userService.insertUserOne(mUser);
		// 1件検索
		mUser = userService.findUserOne(id);
		// Userに変換
		user = modelMapper.map(mUser, User.class);
		// メッセージ作成
		String msg = "以下のように登録されました";
		// ユーザーをセッションに格納
		session.setAttribute("user", user);
		// メッセージをmodelに格納
		model.addAttribute("msg", msg);
		return "exerciseUser/MS102UserRegist";
	}

	// 会員更新画面に遷移する
	@PostMapping("/MS103UserUpDate")
	public String postMS103UserUpDate(Model model, @ModelAttribute User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "exerciseUser/MS103UserUpDate";
	}

	// 会員更新画面に遷移する
	@GetMapping("/MS103UserUpDate")
	public String getMS103UserUpDate(Model model, @Validated User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "exerciseUser/MS103UserUpDate";
		}
		// UserをMUserに変換
		MUser mUser = modelMapper.map(user, MUser.class);
		// DBを更新巣する
		userService.updateUserOne(mUser);
		// 更新した会員を検索
		String id = mUser.getUserID();
		mUser = userService.findUserOne(id);
		// MuserをUserに変換
		user = modelMapper.map(mUser, User.class);
		// メッセージを作成
		String msg = "以下のように会員を更新しました。";
		model.addAttribute("msg", msg);
		model.addAttribute("user", user);
		// 更新した会員をセッションに追加
		session.setAttribute("user", user);

		// 更新画面に遷移する
		return "exerciseUser/MS103UserUpDate";
	}
	
	//会員ログアウトに遷移する
	@PostMapping("/MS190Logout")
	public String postMS190Logout(Model model) {
		User user = (User) session.getAttribute("user");
		//セッションを無効にする
		session.invalidate();
		//modelにユーザーを格納する
		model.addAttribute("user",user);
		return "exerciseUser/MS190Logout";
	}  
}
