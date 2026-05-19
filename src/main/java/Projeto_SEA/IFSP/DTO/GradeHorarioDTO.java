package Projeto_SEA.IFSP.DTO;

public class GradeHorarioDTO {

    private String horario;

    private String seg = "-";
    private String ter = "-";
    private String qua = "-";
    private String qui = "-";
    private String sex = "-";
    private String corSeg;
    private String corTer;
    private String corQua;
    private String corQui;
    private String corSex;

    public GradeHorarioDTO() {

    }

    public GradeHorarioDTO(String horario, String seg, String ter, String qua, String qui, String sex) {
        this.horario = horario;
        this.seg = seg;
        this.ter = ter;
        this.qua = qua;
        this.qui = qui;
        this.sex = sex;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSeg() {
        return seg;
    }

    public void setSeg(String seg) {
        this.seg = seg;
    }

    public String getTer() {
        return ter;
    }

    public void setTer(String ter) {
        this.ter = ter;
    }

    public String getQua() {
        return qua;
    }

    public void setQua(String qua) {
        this.qua = qua;
    }

    public String getQui() {
        return qui;
    }

    public void setQui(String qui) {
        this.qui = qui;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCorSeg() {
        return corSeg;
    }

    public void setCorSeg(String corSeg) {
        this.corSeg = corSeg;
    }

    public String getCorTer() {
        return corTer;
    }

    public void setCorTer(String corTer) {
        this.corTer = corTer;
    }

    public String getCorQua() {
        return corQua;
    }

    public void setCorQua(String corQua) {
        this.corQua = corQua;
    }

    public String getCorQui() {
        return corQui;
    }

    public void setCorQui(String corQui) {
        this.corQui = corQui;
    }

    public String getCorSex() {
        return corSex;
    }

    public void setCorSex(String corSex) {
        this.corSex = corSex;
    }

}
