package com.internousdev.jupiter.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jupiter.dao.CartInfoDAO;
import com.internousdev.jupiter.dao.DestinationInfoDAO;
import com.internousdev.jupiter.dao.UserInfoDAO;
import com.internousdev.jupiter.dto.CartInfoDTO;
import com.internousdev.jupiter.dto.DestinationInfoDTO;
import com.internousdev.jupiter.dto.UserInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementConfirmAction extends ActionSupport implements SessionAware {
	private List<DestinationInfoDTO> destinationInfoDTOList;
	private Map<String, Object> session;

	public String execute() {
		// sessionタイムアウトチェック
		if (session.isEmpty()) {
			return "sessionTimeout";
		}

		String result = ERROR;
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		List<CartInfoDTO> cartList = new ArrayList<CartInfoDTO>();
		String userId = null;

		// ログイン判定 tempUserIdは仮ユーザーID
		// ログインしていない場合cartFlagをsessionで保持しログイン画面へ
		if (session.containsKey("userId")) {
			userId = String.valueOf(session.get("userId"));
		} else {
			session.put("cartFlag", "1");
			return "login";
		}

		// cartInfoDAOからカート情報取得
		cartList = cartInfoDAO.getCartInfoList(userId);
		session.put("cartList", cartList);

		// IDをsessionから取得しintに変換
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		UserInfoDTO userInfo = new UserInfoDTO();
		userInfo = userInfoDAO.getUserInfo(userId);

		int logined = Integer.parseInt(String.valueOf(userInfo.getLogined()));
		if (logined == 1) {
			session.put("logined", logined);
			DestinationInfoDAO destinationInfoDAO = new DestinationInfoDAO();
			destinationInfoDTOList = destinationInfoDAO.getDestinationInfo(String.valueOf(session.get("userId")));
			result = SUCCESS;

		}
		return result;
	}

	public List<DestinationInfoDTO> getDestinationInfoDTOList() {
		return destinationInfoDTOList;
	}

	public void setDestinationInfoDTOList(List<DestinationInfoDTO> destinationInfoDTOList) {
		this.destinationInfoDTOList = destinationInfoDTOList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
