package com.internousdev.jupiter.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jupiter.dao.CartInfoDAO;
import com.internousdev.jupiter.dao.PurchaseHistoryInfoDAO;
import com.internousdev.jupiter.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware {

	private String id;
	private Map<String, Object> session;

	public String execute() throws SQLException {
		//sessionタイムアウトチェック
		if (session.isEmpty()) {
			return "sessionTimeout";
		}

		String result = ERROR;

		String userId = String.valueOf(session.get("userId"));

		//		PurchaseHistoryInfoDAOをインスタンス化、カート情報をsessionから取得しPurchaseHistoryInfoDAOのregistメソッドにて商品購入情報へ登録
		@SuppressWarnings("unchecked")
		List<CartInfoDTO> cartList = (List<CartInfoDTO>) session.get("cartList");

		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
		int count = 0;
		for (CartInfoDTO dto : cartList) {

			count += purchaseHistoryInfoDAO.regist(
					userId,
					dto.getProductId(),
					dto.getProductCount(),
					Integer.parseInt(id),
					dto.getPrice());
		}

		//CartInfoDAOをインスタンス化しdeleteCartAllメソッドを使用しカート情報削除
		if (count > 0) {
			CartInfoDAO cartInfoDAO = new CartInfoDAO();
			count = cartInfoDAO.deleteCartAll(String.valueOf(session.get("userId")));
			if (count > 0) {
				result = SUCCESS;
			}
		}
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}