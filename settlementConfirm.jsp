<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/header.css">
<link rel="stylesheet" href="./css/settlement.css">
<link rel="stylesheet" type=text/css href="./css/jupiter.css">
<script type="text/javascript" src="./js/header.js"></script>
<title>決済確認画面</title>
</head>
<script type="text/javascript" src="./js/settlement.js"></script>
<body>
	<jsp:include page="header.jsp" />
	<div class="contents">
		<h1>決済確認画面</h1>
		<s:form id="settlementForm">
			<s:if
				test="destinationInfoDTOList!=null && destinationInfoDTOList.size()>0">
				<div class="message">宛先を選択してください。</div>

				<table class="product-list">
					<thead>
						<tr>
							<th><s:label value="#" /></th>
							<th><s:label value="姓" /></th>
							<th><s:label value="名" /></th>
							<th><s:label value="ふりがな" /></th>
							<th><s:label value="住所" /></th>
							<th><s:label value="電話番号" /></th>
							<th><s:label value="メールアドレス" /></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="destinationInfoDTOList" status="st">
							<tr>
								<td><s:if test="#st.index == 0">
										<input type="radio" name="id" checked="checked"
											value="<s:property value='id'/>" />
									</s:if> <s:else>
										<input type="radio" name="id" value="<s:property value='id'/>" />
									</s:else></td>
								<td><s:property value="familyName" /><br></td>
								<td><s:property value="firstName" /><br></td>
								<td><s:property value="familyNameKana" /><span> </span> <s:property
										value="firstNameKana" /><br></td>
								<td><s:property value="userAddress" /><br></td>
								<td><s:property value="telNumber" /><br></td>
								<td><s:property value="email" /></td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="7">
								<div class="submit-btn-box">
									<s:submit value="決済" class="btn"
										onclick="goSettlementCompleteAction()" />
								</div>
								<div class="submit-btn-box">
									<s:submit value="新規宛先登録" class="btn"
										onclick="goCreateDestinationAction()" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</s:if>
			<s:else>
				<div class="message">宛先情報がありません。</div>
				<div class="submit-btn-box">
					<s:submit value="新規宛先登録" class="btn"
						onclick="goCreateDestinationAction()" />
				</div>
			</s:else>
		</s:form>
	</div>
</body>
</html>