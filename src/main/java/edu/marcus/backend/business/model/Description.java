package edu.marcus.backend.business.model;

import edu.marcus.backend.persistence.model.DescriptionEntity;

/**
 * TODO: javadoc
 *
 */
public class Description {
	
	private String text;
	private String version;
	private String license;

	public Description(DescriptionEntity descriptionEntity) {
		setLicense(descriptionEntity.getLicense());
		setText(descriptionEntity.getText());
		setVersion(descriptionEntity.getVersion());
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
}
