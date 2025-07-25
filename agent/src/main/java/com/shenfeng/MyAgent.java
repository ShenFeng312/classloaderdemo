package com.shenfeng;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] Agent loaded");
        inst.addTransformer(new Transformer(), true);

        // 尝试重新转换 HashMap
        try {
            Class<?> cls = Class.forName("java.util.HashMap");
            if (inst.isModifiableClass(cls)) {
                inst.retransformClasses(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Transformer implements ClassFileTransformer {
        @Override
        public byte[] transform(Module module, ClassLoader loader,
                                String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) {
            if (!"java/util/HashMap".equals(className)) return null;

            try {
                System.out.println("[Agent] Transforming java.util.HashMap");
                ClassPool pool = ClassPool.getDefault();
                pool.appendClassPath(new LoaderClassPath(loader));
                CtClass cc = pool.get("java.util.HashMap");

                CtMethod putMethod = cc.getDeclaredMethod("put");
                putMethod.insertBefore("System.out.println(\"[Agent] HashMap.put called\");");

                byte[] byteCode = cc.toBytecode();
                cc.detach();
                return byteCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
