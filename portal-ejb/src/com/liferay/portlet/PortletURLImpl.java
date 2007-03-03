/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ArrayUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.Validator;

import java.io.Serializable;

import java.security.Key;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletURLImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletURLImpl implements PortletURL, Serializable {

	public static final boolean APPEND_PARAMETERS = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTLET_URL_APPEND_PARAMETERS));

	public PortletURLImpl(ActionRequestImpl req, String portletName,
						  String plid, boolean action) {

		this(req.getHttpServletRequest(), portletName, plid, action);

		_portletReq = req;
	}

	public PortletURLImpl(RenderRequestImpl req, String portletName,
						  String plid, boolean action) {

		this(req.getHttpServletRequest(), portletName, plid, action);

		_portletReq = req;
	}

	public PortletURLImpl(HttpServletRequest req, String portletName,
						  String plid, boolean action) {

		_req = req;
		_portletName = portletName;
		_plid = plid;
		_secure = req.isSecure();
		_action = action;
		_params = new LinkedHashMap();
	}

	public WindowState getWindowState() {
		return _windowState;
	}

	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_portletReq != null) {
			if (!_portletReq.isWindowStateAllowed(windowState)) {
				throw new WindowStateException(
					windowState.toString(), windowState);
			}
		}

		if (LiferayWindowState.isWindowStatePreserved(
				getWindowState(), windowState)) {

			_windowState = windowState;
		}

		// Clear cache

		_toString = null;
	}

	public void setWindowState(String windowState)
		throws WindowStateException {

		setWindowState(new WindowState(windowState));
	}

	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_portletReq != null) {
			if (!getPortlet().hasPortletMode(
					_portletReq.getResponseContentType(), portletMode)) {

				throw new PortletModeException(
					portletMode.toString(), portletMode);
			}
		}

		_portletMode = portletMode;

		// Clear cache

		_toString = null;
	}

	public void setPortletMode(String portletMode)
		throws PortletModeException {

		setPortletMode(new PortletMode(portletMode));
	}

	public void setParameter(String name, String value) {
		setParameter(name, value, APPEND_PARAMETERS);
	}

	public void setParameter(String name, String value, boolean append) {
		if ((name == null) || (value == null)) {
			throw new IllegalArgumentException();
		}

		setParameter(name, new String[] {value}, append);
	}

	public void setParameter(String name, String[] values) {
		setParameter(name, values, APPEND_PARAMETERS);
	}

	public void setParameter(String name, String[] values, boolean append) {
		if ((name == null) || (values == null)) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				throw new IllegalArgumentException();
			}
		}

		if (append && _params.containsKey(name)) {
			String[] oldValues = (String[])_params.get(name);

			String[] newValues = new String[oldValues.length + values.length];

			ArrayUtil.combine(oldValues, values, newValues);

			_params.put(name, newValues);
		}
		else {
			_params.put(name, values);
		}

		// Clear cache

		_toString = null;
	}

	public void setParameters(Map params) {
		if (params == null) {
			throw new IllegalArgumentException();
		}
		else {
			Map newParams = new LinkedHashMap();

			Iterator itr = params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				Object key = entry.getKey();
				Object value = entry.getValue();

				if (key == null) {
					throw new IllegalArgumentException();
				}
				else if (value == null) {
					throw new IllegalArgumentException();
				}

				if (value instanceof String[]) {
					newParams.put(key, value);
				}
				else {
					throw new IllegalArgumentException();
				}
			}

			_params = newParams;
		}

		// Clear cache

		_toString = null;
	}

	public Map getParameterMap() {
		return _params;
	}

	public void setSecure(boolean secure) throws PortletSecurityException {
		_secure = secure;

		// Clear cache

		_toString = null;
	}

	public String getPortletName() {
		return _portletName;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;

		// Clear cache

		_toString = null;
	}

	public void setAction(boolean action) {
		_action = action;

		// Clear cache

		_toString = null;
	}

	public void setAnchor(boolean anchor) {
		_anchor = anchor;

		// Clear cache

		_toString = null;
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = encrypt;

		// Clear cache

		_toString = null;
	}

	public void setDoAsUserId(String doAsUserId) {
		_doAsUserId = doAsUserId;

		// Clear cache

		_toString = null;
	}

	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		_toString = generateToString();

		return _toString;
	}

	protected String getPlid() {
		return _plid;
	}

	protected Layout getLayout() {
		if (_layout == null) {
			try {
				String layoutId = LayoutImpl.getLayoutId(_plid);
				String ownerId = LayoutImpl.getOwnerId(_plid);

				_layout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);
			}
			catch (Exception e) {
				_log.warn("Layout cannot be found for " + _plid);
			}
		}

		return _layout;
	}

	protected String getLayoutFriendlyURL() {
		return _layoutFriendlyURL;
	}

	protected String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletName);
		}

		return _namespace;
	}

	protected String getParameter(String name) {
		String[] values = (String[])_params.get(name);

		if ((values != null) && (values.length > 0)) {
			return values[0];
		}
		else {
			return null;
		}
	}

	protected Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(_req), _portletName);
			}
			catch (SystemException se) {
				_log.error(se.getMessage());
			}
		}

		return _portlet;
	}

	protected PortletRequest getPortletReq() {
		return _portletReq;
	}

	protected HttpServletRequest getReq() {
		return _req;
	}

	protected boolean isAction() {
		return _action;
	}

	protected boolean isAnchor() {
		return _anchor;
	}

	protected boolean isEncrypt() {
		return _encrypt;
	}

	protected boolean isSecure() {
		return _secure;
	}

	protected String generateToString() {
		StringMaker sm = new StringMaker();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_req.getAttribute(WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portalURL = PortalUtil.getPortalURL(_req, _secure);

		sm.append(portalURL);

		try {
			if (_layoutFriendlyURL == null) {
				Layout layout = getLayout();

				if (layout != null) {
					_layoutFriendlyURL = GetterUtil.getString(
						PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));

					// A virtual host URL will contain the complete path. Since
					// that's not needed, strip the redundant portal URL.

					if (_layoutFriendlyURL.startsWith(portalURL)) {
						_layoutFriendlyURL = _layoutFriendlyURL.substring(
							portalURL.length(), _layoutFriendlyURL.length());
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		Key key = null;

		try {
			if (_encrypt) {
				Company company = PortalUtil.getCompany(_req);

				key = company.getKeyObj();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (Validator.isNull(_layoutFriendlyURL)) {
			sm.append(themeDisplay.getPathMain());
			sm.append("/portal/layout?");

			sm.append("p_l_id");
			sm.append(StringPool.EQUAL);
			sm.append(_processValue(key, _plid));
			sm.append(StringPool.AMPERSAND);
		}
		else {
			sm.append(_layoutFriendlyURL);
			sm.append(StringPool.QUESTION);
		}

		sm.append("p_p_id");
		sm.append(StringPool.EQUAL);
		sm.append(_processValue(key, _portletName));
		sm.append(StringPool.AMPERSAND);

		sm.append("p_p_action");
		sm.append(StringPool.EQUAL);
		sm.append(_action ? _processValue(key, "1") : _processValue(key, "0"));
		sm.append(StringPool.AMPERSAND);

		if (_windowState != null) {
			sm.append("p_p_state");
			sm.append(StringPool.EQUAL);
			sm.append(_processValue(key, _windowState.toString()));
			sm.append(StringPool.AMPERSAND);
		}

		if (_portletMode != null) {
			sm.append("p_p_mode");
			sm.append(StringPool.EQUAL);
			sm.append(_processValue(key, _portletMode.toString()));
			sm.append(StringPool.AMPERSAND);
		}

		sm.append("p_p_col_id");
		sm.append(StringPool.EQUAL);
		sm.append(_processValue(key, portletDisplay.getColumnId()));
		sm.append(StringPool.AMPERSAND);

		sm.append("p_p_col_pos");
		sm.append(StringPool.EQUAL);
		sm.append(_processValue(key, portletDisplay.getColumnPos()));
		sm.append(StringPool.AMPERSAND);

		sm.append("p_p_col_count");
		sm.append(StringPool.EQUAL);
		sm.append(_processValue(key, portletDisplay.getColumnCount()));
		sm.append(StringPool.AMPERSAND);

		if (Validator.isNotNull(_doAsUserId)) {
			try {
				Company company = PortalUtil.getCompany(_req);

				sm.append("doAsUserId");
				sm.append(StringPool.EQUAL);
				sm.append(_processValue(company.getKeyObj(), _doAsUserId));
				sm.append(StringPool.AMPERSAND);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			String doAsUserId = themeDisplay.getDoAsUserId();

			if (Validator.isNotNull(doAsUserId)) {
				sm.append("doAsUserId");
				sm.append(StringPool.EQUAL);
				sm.append(_processValue(key, doAsUserId));
				sm.append(StringPool.AMPERSAND);
			}
		}

		Iterator itr = _params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String name = (String)entry.getKey();
			String[] values = (String[])entry.getValue();

			for (int i = 0; i < values.length; i++) {
				sm.append(getNamespace());
				sm.append(name);
				sm.append(StringPool.EQUAL);
				sm.append(_processValue(key, values[i]));

				if ((i + 1 < values.length) || itr.hasNext()) {
					sm.append(StringPool.AMPERSAND);
				}
			}
		}

		if (_encrypt) {
			sm.append(StringPool.AMPERSAND + WebKeys.ENCRYPT + "=1");
		}

		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.PORTLET_URL_ANCHOR_ENABLE))) {

			if (_anchor && (_windowState != null) &&
				(!_windowState.equals(WindowState.MAXIMIZED)) &&
				(!_windowState.equals(LiferayWindowState.EXCLUSIVE)) &&
				(!_windowState.equals(LiferayWindowState.POP_UP))) {

				if (sm.lastIndexOf(StringPool.AMPERSAND) != (sm.length() - 1)) {
					sm.append(StringPool.AMPERSAND);
				}

				sm.append("#p_").append(_portletName);
			}
		}

		return sm.toString();
	}

	private String _processValue(Key key, int value) {
		return _processValue(key, Integer.toString(value));
	}

	private String _processValue(Key key, String value) {
		if (key == null) {
			return Http.encodeURL(value);
		}
		else {
			try {
				return Http.encodeURL(Encryptor.encrypt(key, value));
			}
			catch (EncryptorException ee) {
				return value;
			}
		}
	}

	private static Log _log = LogFactory.getLog(PortletURLImpl.class);

	private HttpServletRequest _req;
	private PortletRequest _portletReq;
	private String _portletName;
	private Portlet _portlet;
	private String _namespace;
	private String _plid;
	private Layout _layout;
	private String _layoutFriendlyURL;
	private boolean _action;
	private WindowState _windowState;
	private PortletMode _portletMode;
	private Map _params;
	private boolean _secure;
	private boolean _anchor = true;
	private boolean _encrypt = false;
	private String _doAsUserId;
	private String _toString;

}