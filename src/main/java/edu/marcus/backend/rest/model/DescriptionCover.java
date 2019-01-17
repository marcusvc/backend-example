package edu.marcus.backend.rest.model;

import edu.marcus.backend.business.model.Description;

/**
 * TODO: javadoc
 *
 */
public class DescriptionCover {
	
	private String text;
	private String version;
	private String license;

	public DescriptionCover(Description description) {
		setLicense(description.getLicense());
		setText(description.getText());
		setVersion(description.getVersion());
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
