package edu.marcus.backend.persistence.model;

/**
 * TODO: javadoc
 *
 */
public class DescriptionEntity {
	private String text = "This is a sample project to present a backend API REST";
	private String version = "0.0.1-SNAPSHOT";
	private String license = "Apache 2.0";
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
