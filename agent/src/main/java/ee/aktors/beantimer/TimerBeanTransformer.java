package ee.aktors.beantimer;

import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.AnnotationUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.log4j.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Proxy;
import java.security.ProtectionDomain;

/**
 * Agent's transformer. Wrapping a bean body between execution duration measurement and reading annotations of the bean
 */
public class TimerBeanTransformer implements ClassFileTransformer {

    private final static Logger LOG = Logger.getLogger(TimerBeanTransformer.class);

    public static final String CLASSPATH_PROXY = "com.sun.proxy.$Proxy";
    public static final String CLASSPATH_CONFIGURATION = "org.springframework.context.annotation.Configuration";
    public static final String CLASSPATH_BEAN = "org.springframework.context.annotation.Bean";
    public static final String CLASSPATH_SCOPE = "org.springframework.context.annotation.Scope";
    public static final String CLASSPATH_PRIMARY = "org.springframework.context.annotation.Primary";
    public static final String CLASSPATH_QUALIFIER = "org.springframework.beans.factory.annotation.Qualifier";
    public static final String CLASSPATH_NAMED = "javax.inject.Named";

    public static final String ARGUMENT_NAME = "name=";

    public static final String SPRING_BEAN_DEFAULT_SCOPE = "singleton";

    final ClassFilter classFilter;

    public TimerBeanTransformer(ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

        if (classFilter.matches(s)) {
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(classFilter.transformClasspath(s));

                Object[] classAnnotations = cc.getAnnotations();
                for (Object classAnnotation : classAnnotations) {

                    String className = classAnnotation.getClass().getName();
                    if (className.startsWith(CLASSPATH_PROXY)) {

                        Proxy proxy;
                        try {
                            proxy = (Proxy) classAnnotation;

                            if (proxy.toString().endsWith(CLASSPATH_CONFIGURATION)) {

                                CtMethod[] methods = cc.getMethods();
                                for (CtMethod method : methods) {
                                    findBeanAnnotations(method);
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("Problem with casting proxy", e);
                        }
                    }

                }
                byte[] byteCode = cc.toBytecode();
                cc.detach();
                return byteCode;
            } catch (Exception e) {
                LOG.error("Issue with instrumentation", e);
            }
        }

        return null;
    }

    private void findBeanAnnotations(CtMethod method) throws Exception {
        Object[] methodAnnotations = method.getAnnotations();

        String scope = SPRING_BEAN_DEFAULT_SCOPE;
        String qualifier = null;
        String beanName = null;
        String named = null;
        boolean primaryAnnotationPresent = false;
        boolean beanAnnotationPresent = false;
        for (Object methodAnnotation : methodAnnotations) {
            String proxy = methodAnnotation.toString();

            if (proxy.contains(CLASSPATH_SCOPE)) {
                scope = AnnotationUtil.parseFirstArgumentValue(proxy);
            }
            if (proxy.contains(CLASSPATH_QUALIFIER)) {
                qualifier = AnnotationUtil.parseFirstArgumentValue(proxy);
            }
            if (proxy.contains(CLASSPATH_NAMED)) {
                named = AnnotationUtil.parseFirstArgumentValue(proxy);
            }
            if (proxy.contains(CLASSPATH_PRIMARY)) {
                primaryAnnotationPresent = true;
            }
            if (proxy.contains(CLASSPATH_BEAN)) {
                beanAnnotationPresent = true;
                if (proxy.contains(ARGUMENT_NAME)) {
                    beanName = AnnotationUtil.parseFirstArgumentValue(proxy);
                }
            }
        }

        if (beanAnnotationPresent) {
            Measurement measurement = new Measurement(method.getName(), method.getReturnType().getSimpleName(), scope, null, null, primaryAnnotationPresent);
            addInstrumentation(method, measurement);
        }
    }

    private void addInstrumentation(CtMethod method, Measurement m) throws Exception {
        method.addLocalVariable("currentMillis", CtClass.longType);
        method.addLocalVariable("elapsedTime", CtClass.longType);
        method.addLocalVariable("primaryBean", CtClass.booleanType);
        method.insertBefore("primaryBean = " + m.getPrimary().toString() + ";");
        method.insertBefore("currentMillis = System.currentTimeMillis();");
        method.insertBefore("elapsedTime = System.currentTimeMillis();");
        method.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime; " +
                "ee.aktors.beantimer.util.TimingUtil.addMeasurement(\"" + m.getBeanName() + "\",\"" + m.getBeanType() + "\",\"" + m.getBeanScope() + "\", elapsedTime, currentMillis, primaryBean );}");


//        method.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime; " +
//                        "ee.aktors.beantimer.model.Measurement m " +
//                        "= new ee.aktors.beantimer.model.Measurement(\"" + m.getBeanName() + "\",\"" + m.getBeanType() + "\",\""+ m.getBeanScope() +"\", elapsedTime, currentMillis); " +
//                " ee.aktors.beantimer.util.TimingUtil.addMeasurement(m);}");

    }

}
