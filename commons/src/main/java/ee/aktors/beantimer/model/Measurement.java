package ee.aktors.beantimer.model;

/**
 *
 */
public class Measurement {

    private String beanName;
    private String beanType;
    private Integer duration;

    public Measurement(String beanName, String beanType, Integer duration) {
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

    @Override
    public String toString() {
        return String.format("{\"beanName\":\"%s\",\"beanType\":\"%s\",\"duration\":\"%s\"}", getBeanName(), getBeanType(), getDuration());
    }
}
