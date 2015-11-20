package team.ideart.shiguang_app.app.entity;

/**
 * SidebarItem
 *
 * @author Allen Jin
 * @date 2015/11/20
 */
public class SidebarItem {

    private String id;
    private int logoRes;
    private int logoActiveRes;
    private int txtRes;

    public SidebarItem(String id, int logoRes, int logoActiveRes, int txtRes) {
        this.id = id;
        this.logoRes = logoRes;
        this.logoActiveRes = logoActiveRes;
        this.txtRes = txtRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLogoRes() {
        return logoRes;
    }

    public void setLogoRes(int logoRes) {
        this.logoRes = logoRes;
    }

    public int getTxtRes() {
        return txtRes;
    }

    public void setTxtRes(int txtRes) {
        this.txtRes = txtRes;
    }

    public int getLogoActiveRes() {
        return logoActiveRes;
    }

    public void setLogoActiveRes(int logoActiveRes) {
        this.logoActiveRes = logoActiveRes;
    }
}
