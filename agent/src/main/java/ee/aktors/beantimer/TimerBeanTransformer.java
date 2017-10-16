package ee.aktors.beantimer;

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
 *
 */
public class TimerBeanTransformer implements ClassFileTransformer {

    private final static Logger LOG = Logger.getLogger(Agent.class);

    public static final String CLASSPATH_PROXY = "com.sun.proxy.$Proxy";
    public static final String CLASSPATH_CONFIGURATION = "org.springframework.context.annotation.Configuration";
    public static final String CLASSPATH_BEAN = "org.springframework.context.annotation.Bean";
    public static final String CLASSPATH_SCOPE = "org.springframework.context.annotation.Scope";
    public static final String CLASSPATH_PRIMARY = "org.springframework.context.annotation.Primary";
    public static final String CLASSPATH_QUALIFIER = "org.springframework.beans.factory.annotation.Qualifier";
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
        boolean primaryAnnotationPresent = false;
        boolean beanAnnotationPresent = false;
        for (Object methodAnnotation : methodAnnotations) {
            String proxy = methodAnnotation.toString();

            if (proxy.contains(CLASSPATH_SCOPE)) {
                scope = AnnotationUtil.parseValue(proxy);
            }
            if (proxy.contains(CLASSPATH_QUALIFIER)) {
                qualifier = AnnotationUtil.parseValue(proxy);
            }
            if (proxy.contains(CLASSPATH_PRIMARY)) {
                primaryAnnotationPresent = true;
            }
            if (proxy.contains(CLASSPATH_BEAN)) {
                beanAnnotationPresent = true;
            }
        }

        if (beanAnnotationPresent) {
            addInstrumentation(method, scope);
        }
    }

    private void addInstrumentation(CtMethod method, String scope) throws Exception {
        String methodName = method.getName();
        String returnType = method.getReturnType().getSimpleName();
        method.addLocalVariable("currentMillis", CtClass.longType);
        method.addLocalVariable("elapsedTime", CtClass.longType);
        method.insertBefore("currentMillis = System.currentTimeMillis();");
        method.insertBefore("elapsedTime = System.nanoTime();");
        method.insertAfter("{elapsedTime = System.nanoTime() - elapsedTime; " +
                "ee.aktors.beantimer.util.TimingUtil.addMeasurement(\"" + methodName + "\",\"" + returnType + "\",\"" + scope + "\", elapsedTime, currentMillis);}");
    }

}
