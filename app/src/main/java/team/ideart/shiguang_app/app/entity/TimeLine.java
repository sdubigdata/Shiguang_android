package team.ideart.shiguang_app.app.entity;

import java.io.Serializable;

/**
 * TimeLine
 *
 * @author Allen Jin
 * @date 2015/11/21
 */
public class TimeLine implements Serializable {

    private int id;
    private long date;
    private String weather;
    private int color;
    private String content;

    private String resUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    @Override
    public String toString() {
        return "TimeLine{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                ", color=" + color +
                ", content='" + content + '\'' +
                ", resUrl='" + resUrl + '\'' +
                '}';
    }
}
