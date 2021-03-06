package com.cgiser.moka.rune.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgiser.keel.http.utils.HttpSpringUtils;
import com.cgiser.moka.action.AbstractAction;
import com.cgiser.moka.manager.UserRuneManager;
import com.cgiser.moka.model.Role;
import com.cgiser.moka.model.UserRune;
import com.cgiser.moka.result.ReturnType;

public class GetUserRuneAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ReturnType<List<UserRune>> returnType = new ReturnType<List<UserRune>>();
		try{
			Role role = super.getCurrentRole(request);
			if(null==role){
				returnType.setMsg("您还没登录哦！");
				returnType.setStatus(0);
				super.printReturnType2Response(response, returnType);
				return null;
			}
			UserRuneManager userRuneManager = (UserRuneManager)HttpSpringUtils.getBean("userRuneManager");
			List<UserRune> runes = userRuneManager.getUserRune(role.getRoleId());
			returnType.setStatus(1);
			returnType.setValue(runes);
			super.printReturnType2Response(response, returnType);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
