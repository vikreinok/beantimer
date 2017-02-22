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

    final static Logger LOG = Logger.getLogger(Agent.class);
    public static final String CLASSPATH_PROXY = "com.sun.proxy.$Proxy";
    public static final String CLASSPATH_CONFIGURATION = "org.springframework.context.annotation.Configuration";

    ClassFilter classFilter = new ClassFilter();

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
                                LOG.error(proxy);

                                CtMethod[] methods = cc.getMethods();
                                for (CtMethod method : methods) {
                                    printBeanAnnotations(method);
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("Problem with casting proxy " + e.getMessage());
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

    private void printBeanAnnotations(CtMethod method) throws Exception {
        Object[] methodAnnotations = method.getAnnotations();

        Proxy methodProxy = null;
        for (Object methodAnnotation : methodAnnotations) {
            methodProxy = (Proxy) methodAnnotation;
            if (methodProxy.toString().endsWith("Bean")) {
                LOG.error("    " + methodProxy);
                LOG.error("    " + method.getName());

                addTimer(method);
            }
        }
    }

    private byte[] addTimer(CtClass cc) throws Exception {
        CtMethod m = cc.getDeclaredMethod("run");
        m.addLocalVariable("elapsedTime", CtClass.longType);
        m.insertBefore("elapsedTime = System.currentTimeMillis();");
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
        byte[] byteCode = cc.toBytecode();
        cc.detach();
        return byteCode;
    }

    private void addTimer(CtMethod method) throws Exception {
        CtMethod m = method;
        String methodName = String.format("%-100s", method.getName());
        String returnType = String.format("%-55s", method.getReturnType().getSimpleName());
        m.addLocalVariable("elapsedTime", CtClass.longType);
        m.insertBefore("elapsedTime = System.currentTimeMillis();");
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime; " +
                "System.err.println(\"Bean  " + returnType + " " + methodName + "  in ms: \" + elapsedTime);}");
    }

}
