package lib;

/**
 * Created by orcusz on 15/01/15.
 */
public class Planning {
    private String name;
    private String type;
    private String teacher;
    private String dateStart;
    private String dateEnd;
    private String backColor;

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getName() {

        return name;
    }

    public String getType() {
        return type;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public Planning(){

    }

    public Planning(String name, String teacher, String type, String dateStart, String dateEnd, String backColor){
        setType(type);
        setDateEnd(dateEnd);
        setDateStart(dateStart);
        setName(name);
        setTeacher(teacher);
        setBackColor(backColor);
    }
}
