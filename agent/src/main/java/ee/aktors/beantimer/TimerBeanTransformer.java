package ee.aktors.beantimer;

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

    ClassFilter classFilter;

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
                            LOG.error("Problem with casting proxy ", e );
                        }
                    }

                }
                byte[] byteCode = cc.toBytecode();
                cc.detach();
                return byteCode;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    private void findBeanAnnotations(CtMethod method) throws Exception {
        Object[] methodAnnotations = method.getAnnotations();

        Proxy methodProxy;
        for (Object methodAnnotation : methodAnnotations) {
            methodProxy = (Proxy) methodAnnotation;
            if (methodProxy.toString().endsWith("Bean")) {
                addTimer(method);
            }
        }
    }

    private void addTimer(CtMethod method) throws Exception {
        String methodName = String.format("%-70s", method.getName());
        String returnType = String.format("%45s", method.getReturnType().getSimpleName());
        String beanName = String.format("[%s] %s", returnType, methodName);
        method.addLocalVariable("elapsedTime", CtClass.intType);
        method.insertBefore("elapsedTime = System.currentTimeMillis();");
        method.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime; " +
                "ee.aktors.beantimer.util.TimingUtil.addMeasurement(\"" + beanName + "\", \""+ returnType + "\", elapsedTime);}");
    }

}
