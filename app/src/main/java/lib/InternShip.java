package lib;

/**
 * Created by orcusz on 23/01/15.
 */
public class InternShip{
    private String title;
    private String company;
    private String duration;
    private String missionSummary;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getMissionSummary() {
            return missionSummary;
        }

        public void setMissionSummary(String missionSummary) {
            this.missionSummary = missionSummary;
        }

    public InternShip(String _title, String _company, String _duration, String _missionSummary){
        setTitle(_title);
        setCompany(_company);
        setDuration(_duration);
        setMissionSummary(_missionSummary);
    }
}
