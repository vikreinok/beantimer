package ee.aktors.beantimer.model;

/**
 *
 */
public class Measurement {

    private final String beanName;
    private final String beanType;
    private final String beanScope;
    private final Long duration;
    private final Long initialisationStartTimeMillis;
    private final Boolean primaryBean;

    public Measurement(String beanName, String beanType, String beanScope, Long duration, Long initialisationStartTimeMillis, Boolean primaryBean) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.beanScope = beanScope;
        this.duration = duration;
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
        this.primaryBean = primaryBean;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public String getBeanScope() {
        return beanScope;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getInitialisationStartTimeMillis() {
        return initialisationStartTimeMillis;
    }

    public Boolean getPrimaryBean() {
        return primaryBean;
    }

    @Override
    public String toString() {
        return String.format("{\"beanName\":\"%s\",\"beanType\":\"%s\",\"beanScope\":\"%s\",\"duration\":\"%s\",\"initialisationStartTimeMillis\":\"%s\",\"primaryBean\":%s }", getBeanName(), getBeanType(), getBeanScope(),  getDuration(), getInitialisationStartTimeMillis(), getPrimaryBean());
    }
}
