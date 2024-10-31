package com.medilabo.riskanalyzer.model;

public class Note
{
	String id;
	int patientId;
	String content;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getPatientId()
	{
		return patientId;
	}

	public void setPatientId(int patientId)
	{
		this.patientId = patientId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
