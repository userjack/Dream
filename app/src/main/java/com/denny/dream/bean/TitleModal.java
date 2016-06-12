package com.denny.dream.bean;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TitleModal {

    /**
     * id : 1
     * name : 英雄联盟
     * slug : lol
     * first_letter : L
     * status : 0
     * prompt : 1
     * image : http://image.quanmin.tv/8d8dafca09dea47bc4083196c16f4b6ajpg
     * thumb : http://image.quanmin.tv/2b8726eb9c0c369a353153d33b4dae1fpng
     * priority : 1
     */

    private int id;
    private String name;
    private String slug;
    private String first_letter;
    private int status;
    private int prompt;
    private String image;
    private String thumb;
    private int priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrompt() {
        return prompt;
    }

    public void setPrompt(int prompt) {
        this.prompt = prompt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
