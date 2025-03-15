package com.ctet.common;

import org.springframework.stereotype.Component;

@Component
public class PropertyConfig {
	String baseUrl;

	String hostName;

	String sendFrom;

	String sendFromName;

	String sendFromPassword;

	String smtpPort;

	String testLinkHtmlLocation;

	String testLinkHtml_Generic_Location;

	String resultLinkHtmlLocation;

	String screenShotFolder;

	String defaultReferenceConfigFileLocation;

	String tenantsConfigLocation;

	String tomcatDeployLocation;

	String remoteBaseUrl;

	String pmdServerBaseUrl = "";

	String fileServerLocation;

	String fileServerWebUrl;

	String fullStackCodeLocation = "/opt/eclipse_che/instance/data/workspaces";

	String codeQualityServerLink = "http://13.233.2.97:9000/dashboard?id=";

	String codeQualityEmailTemplateLocation;

	/**
	 * Java projects
	 */
	String sonarAnalysisFileLocation;

	String eclipseCheJsonLocation;

	String reportFilesLocation;

	String modulesImageLocation;

	String modulesVideoLocation;

	String moduleItemsImageLocation;

	String moduleItemsVideoLocation;

	String sendCredentialsToStudent;

	String shareModuleoStudent;

	String sendTestResultInfoSubjective;

	String cvLocation;

	String bigBlueSalt;

	public String getBigBlueSalt() {
		return bigBlueSalt;
	}

	public void setBigBlueSalt(String bigBlueSalt) {
		this.bigBlueSalt = bigBlueSalt;
	}

	public String getCvLocation() {
		return cvLocation;
	}

	public void setCvLocation(String cvLocation) {
		this.cvLocation = cvLocation;
	}

	public String getFullStackWorkspaceFolder() {
		return fullStackWorkspaceFolder;
	}

	public void setFullStackWorkspaceFolder(String fullStackWorkspaceFolder) {
		this.fullStackWorkspaceFolder = fullStackWorkspaceFolder;
	}

	/**
	 * PhP projects
	 */
	String sonalAnalysisFilePHPLocation;

	String sonarAnalysisFileAngularLocation;

	String sonarAnalysisFileDotNetLocation;

	String fullStackReviewTemplate;

	String fullStackWorkspaceFolder;

	String imageQuestionFolder;

	String videoQuestionFolder;

	String subjectiveScoring = "no";

	String companyName;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getSendFromName() {
		return sendFromName;
	}

	public void setSendFromName(String sendFromName) {
		this.sendFromName = sendFromName;
	}

	public String getSendFromPassword() {
		return sendFromPassword;
	}

	public void setSendFromPassword(String sendFromPassword) {
		this.sendFromPassword = sendFromPassword;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getTestLinkHtmlLocation() {
		return testLinkHtmlLocation;
	}

	public void setTestLinkHtmlLocation(String testLinkHtmlLocation) {
		this.testLinkHtmlLocation = testLinkHtmlLocation;
	}

	public String getResultLinkHtmlLocation() {
		return resultLinkHtmlLocation;
	}

	public void setResultLinkHtmlLocation(String resultLinkHtmlLocation) {
		this.resultLinkHtmlLocation = resultLinkHtmlLocation;
	}

	public String getScreenShotFolder() {
		return screenShotFolder;
	}

	public void setScreenShotFolder(String screenShotFolder) {
		this.screenShotFolder = screenShotFolder;
	}

	public String getDefaultReferenceConfigFileLocation() {
		return defaultReferenceConfigFileLocation;
	}

	public void setDefaultReferenceConfigFileLocation(String defaultReferenceConfigFileLocation) {
		this.defaultReferenceConfigFileLocation = defaultReferenceConfigFileLocation;
	}

	public String getTenantsConfigLocation() {
		return tenantsConfigLocation;
	}

	public void setTenantsConfigLocation(String tenantsConfigLocation) {
		this.tenantsConfigLocation = tenantsConfigLocation;
	}

	public String getTomcatDeployLocation() {
		return tomcatDeployLocation;
	}

	public void setTomcatDeployLocation(String tomcatDeployLocation) {
		this.tomcatDeployLocation = tomcatDeployLocation;
	}

	public String getRemoteBaseUrl() {
		return remoteBaseUrl;
	}

	public void setRemoteBaseUrl(String remoteBaseUrl) {
		this.remoteBaseUrl = remoteBaseUrl;
	}

	public String getPmdServerBaseUrl() {
		return pmdServerBaseUrl;
	}

	public void setPmdServerBaseUrl(String pmdServerBaseUrl) {
		this.pmdServerBaseUrl = pmdServerBaseUrl;
	}

	public String getTestLinkHtml_Generic_Location() {
		return testLinkHtml_Generic_Location;
	}

	public void setTestLinkHtml_Generic_Location(String testLinkHtml_Generic_Location) {
		this.testLinkHtml_Generic_Location = testLinkHtml_Generic_Location;
	}

	public String getFileServerLocation() {
		return fileServerLocation;
	}

	public void setFileServerLocation(String fileServerLocation) {
		this.fileServerLocation = fileServerLocation;
	}

	public String getFileServerWebUrl() {
		return fileServerWebUrl;
	}

	public void setFileServerWebUrl(String fileServerWebUrl) {
		this.fileServerWebUrl = fileServerWebUrl;
	}

	public String getFullStackCodeLocation() {
		return fullStackCodeLocation;
	}

	public void setFullStackCodeLocation(String fullStackCodeLocation) {
		this.fullStackCodeLocation = fullStackCodeLocation;
	}

	public String getCodeQualityServerLink() {
		return codeQualityServerLink;
	}

	public void setCodeQualityServerLink(String codeQualityServerLink) {
		this.codeQualityServerLink = codeQualityServerLink;
	}

	public String getCodeQualityEmailTemplateLocation() {
		return codeQualityEmailTemplateLocation;
	}

	public void setCodeQualityEmailTemplateLocation(String codeQualityEmailTemplateLocation) {
		this.codeQualityEmailTemplateLocation = codeQualityEmailTemplateLocation;
	}

	public String getSonarAnalysisFileLocation() {
		return sonarAnalysisFileLocation;
	}

	public void setSonarAnalysisFileLocation(String sonarAnalysisFileLocation) {
		this.sonarAnalysisFileLocation = sonarAnalysisFileLocation;
	}

	public String getFullStackReviewTemplate() {
		return fullStackReviewTemplate;
	}

	public void setFullStackReviewTemplate(String fullStackReviewTemplate) {
		this.fullStackReviewTemplate = fullStackReviewTemplate;
	}

	public String getSonalAnalysisFilePHPLocation() {
		return sonalAnalysisFilePHPLocation;
	}

	public void setSonalAnalysisFilePHPLocation(String sonalAnalysisFilePHPLocation) {
		this.sonalAnalysisFilePHPLocation = sonalAnalysisFilePHPLocation;
	}

	public String getSonarAnalysisFileAngularLocation() {
		return sonarAnalysisFileAngularLocation;
	}

	public void setSonarAnalysisFileAngularLocation(String sonarAnalysisFileAngularLocation) {
		this.sonarAnalysisFileAngularLocation = sonarAnalysisFileAngularLocation;
	}

	public String getSonarAnalysisFileDotNetLocation() {
		return sonarAnalysisFileDotNetLocation;
	}

	public void setSonarAnalysisFileDotNetLocation(String sonarAnalysisFileDotNetLocation) {
		this.sonarAnalysisFileDotNetLocation = sonarAnalysisFileDotNetLocation;
	}

	public String getEclipseCheJsonLocation() {
		return eclipseCheJsonLocation;
	}

	public void setEclipseCheJsonLocation(String eclipseCheJsonLocation) {
		this.eclipseCheJsonLocation = eclipseCheJsonLocation;
	}

	public String getReportFilesLocation() {
		return reportFilesLocation;
	}

	public void setReportFilesLocation(String reportFilesLocation) {
		this.reportFilesLocation = reportFilesLocation;
	}

	public String getModulesImageLocation() {
		return modulesImageLocation;
	}

	public void setModulesImageLocation(String modulesImageLocation) {
		this.modulesImageLocation = modulesImageLocation;
	}

	public String getModulesVideoLocation() {
		return modulesVideoLocation;
	}

	public void setModulesVideoLocation(String modulesVideoLocation) {
		this.modulesVideoLocation = modulesVideoLocation;
	}

	public String getModuleItemsImageLocation() {
		return moduleItemsImageLocation;
	}

	public void setModuleItemsImageLocation(String moduleItemsImageLocation) {
		this.moduleItemsImageLocation = moduleItemsImageLocation;
	}

	public String getModuleItemsVideoLocation() {
		return moduleItemsVideoLocation;
	}

	public void setModuleItemsVideoLocation(String moduleItemsVideoLocation) {
		this.moduleItemsVideoLocation = moduleItemsVideoLocation;
	}

	public String getSendCredentialsToStudent() {
		return sendCredentialsToStudent;
	}

	public void setSendCredentialsToStudent(String sendCredentialsToStudent) {
		this.sendCredentialsToStudent = sendCredentialsToStudent;
	}

	public String getShareModuleoStudent() {
		return shareModuleoStudent;
	}

	public void setShareModuleoStudent(String shareModuleoStudent) {
		this.shareModuleoStudent = shareModuleoStudent;
	}

	public String getImageQuestionFolder() {
		return imageQuestionFolder;
	}

	public void setImageQuestionFolder(String imageQuestionFolder) {
		this.imageQuestionFolder = imageQuestionFolder;
	}

	public String getVideoQuestionFolder() {
		return videoQuestionFolder;
	}

	public void setVideoQuestionFolder(String videoQuestionFolder) {
		this.videoQuestionFolder = videoQuestionFolder;
	}

	public String getSubjectiveScoring() {
		return subjectiveScoring;
	}

	public void setSubjectiveScoring(String subjectiveScoring) {
		this.subjectiveScoring = subjectiveScoring;
	}

	public String getSendTestResultInfoSubjective() {
		return sendTestResultInfoSubjective;
	}

	public void setSendTestResultInfoSubjective(String sendTestResultInfoSubjective) {
		this.sendTestResultInfoSubjective = sendTestResultInfoSubjective;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
