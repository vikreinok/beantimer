package ee.aktors.beantimer.model;

/**
 *
 */
public class Measurement {

    private final String beanName;
    private final String beanType;
    private final Long duration;
    private final Long initialisationStartTimeMillis;

    public Measurement(String beanName, String beanType, Long duration, Long initialisationStartTimeMillis) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.duration = duration;
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getInitialisationStartTimeMillis() {
        return initialisationStartTimeMillis;
    }

    @Override
    public String toString() {
        return String.format("{\"beanName\":\"%s\",\"beanType\":\"%s\",\"duration\":\"%s\",\"initialisationStartTimeMillis\":\"%s\" }", getBeanName(), getBeanType(), getDuration(), getInitialisationStartTimeMillis());
    }
}
