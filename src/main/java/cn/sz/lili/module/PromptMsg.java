package cn.sz.lili.module;

import java.io.Serializable;

/**
 * Created by chenlei2 on 2017/6/29 0029.
 */
public class PromptMsg implements Serializable{
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
