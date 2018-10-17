package com.example.academicsmanager;

public class Note {
	private String title;
    private String content;
    private String subject;
    private int id;
    public Note()
    {
        this.title=null;
        this.content=null;
        this.subject=null;
    }
    public Note(String title, String content, String subject)
    {
        super();
        this.title = title;
        this.content = content;
        this.subject=subject;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
    	return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSubject() {
    	return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
