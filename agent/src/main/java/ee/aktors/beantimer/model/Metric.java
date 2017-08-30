package ee.aktors.beantimer.model;

/**
 *
 */
public class Metric {

    private String beanName;
    private String beanType;
    private Integer duration;

    public Metric(String beanName, String beanType, Integer duration) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.duration = duration;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public Integer getDuration() {
        return duration;
    }
}
