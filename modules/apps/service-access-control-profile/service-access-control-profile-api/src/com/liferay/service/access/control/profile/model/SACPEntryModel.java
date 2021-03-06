/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.service.access.control.profile.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LocalizedModel;
import com.liferay.portal.model.StagedAuditedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the SACPEntry service. Represents a row in the &quot;SACPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.service.access.control.profile.model.impl.SACPEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntry
 * @see com.liferay.service.access.control.profile.model.impl.SACPEntryImpl
 * @see com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl
 * @generated
 */
@ProviderType
public interface SACPEntryModel extends BaseModel<SACPEntry>, LocalizedModel,
	StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a s a c p entry model instance should use the {@link SACPEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this s a c p entry.
	 *
	 * @return the primary key of this s a c p entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this s a c p entry.
	 *
	 * @param primaryKey the primary key of this s a c p entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this s a c p entry.
	 *
	 * @return the uuid of this s a c p entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this s a c p entry.
	 *
	 * @param uuid the uuid of this s a c p entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the sacp entry ID of this s a c p entry.
	 *
	 * @return the sacp entry ID of this s a c p entry
	 */
	public long getSacpEntryId();

	/**
	 * Sets the sacp entry ID of this s a c p entry.
	 *
	 * @param sacpEntryId the sacp entry ID of this s a c p entry
	 */
	public void setSacpEntryId(long sacpEntryId);

	/**
	 * Returns the company ID of this s a c p entry.
	 *
	 * @return the company ID of this s a c p entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this s a c p entry.
	 *
	 * @param companyId the company ID of this s a c p entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this s a c p entry.
	 *
	 * @return the user ID of this s a c p entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this s a c p entry.
	 *
	 * @param userId the user ID of this s a c p entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this s a c p entry.
	 *
	 * @return the user uuid of this s a c p entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this s a c p entry.
	 *
	 * @param userUuid the user uuid of this s a c p entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this s a c p entry.
	 *
	 * @return the user name of this s a c p entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this s a c p entry.
	 *
	 * @param userName the user name of this s a c p entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this s a c p entry.
	 *
	 * @return the create date of this s a c p entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this s a c p entry.
	 *
	 * @param createDate the create date of this s a c p entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this s a c p entry.
	 *
	 * @return the modified date of this s a c p entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this s a c p entry.
	 *
	 * @param modifiedDate the modified date of this s a c p entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this s a c p entry.
	 *
	 * @return the name of this s a c p entry
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this s a c p entry.
	 *
	 * @param name the name of this s a c p entry
	 */
	public void setName(String name);

	/**
	 * Returns the title of this s a c p entry.
	 *
	 * @return the title of this s a c p entry
	 */
	public String getTitle();

	/**
	 * Returns the localized title of this s a c p entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this s a c p entry
	 */
	@AutoEscape
	public String getTitle(Locale locale);

	/**
	 * Returns the localized title of this s a c p entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this s a c p entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getTitle(Locale locale, boolean useDefault);

	/**
	 * Returns the localized title of this s a c p entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this s a c p entry
	 */
	@AutoEscape
	public String getTitle(String languageId);

	/**
	 * Returns the localized title of this s a c p entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this s a c p entry
	 */
	@AutoEscape
	public String getTitle(String languageId, boolean useDefault);

	@AutoEscape
	public String getTitleCurrentLanguageId();

	@AutoEscape
	public String getTitleCurrentValue();

	/**
	 * Returns a map of the locales and localized titles of this s a c p entry.
	 *
	 * @return the locales and localized titles of this s a c p entry
	 */
	public Map<Locale, String> getTitleMap();

	/**
	 * Sets the title of this s a c p entry.
	 *
	 * @param title the title of this s a c p entry
	 */
	public void setTitle(String title);

	/**
	 * Sets the localized title of this s a c p entry in the language.
	 *
	 * @param title the localized title of this s a c p entry
	 * @param locale the locale of the language
	 */
	public void setTitle(String title, Locale locale);

	/**
	 * Sets the localized title of this s a c p entry in the language, and sets the default locale.
	 *
	 * @param title the localized title of this s a c p entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setTitle(String title, Locale locale, Locale defaultLocale);

	public void setTitleCurrentLanguageId(String languageId);

	/**
	 * Sets the localized titles of this s a c p entry from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this s a c p entry
	 */
	public void setTitleMap(Map<Locale, String> titleMap);

	/**
	 * Sets the localized titles of this s a c p entry from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this s a c p entry
	 * @param defaultLocale the default locale
	 */
	public void setTitleMap(Map<Locale, String> titleMap, Locale defaultLocale);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public Object clone();

	@Override
	public int compareTo(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.liferay.service.access.control.profile.model.SACPEntry> toCacheModel();

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry toEscapedModel();

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}