package org.mengyun.tcctransaction.feign.enhance;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author xiaobzhou
 * date 2019-03-08 13:58
 */
public class FeignClientDecoratorClassGenerator {

    public static Class generate(Class clientInterfaceClassType) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();

        CtClass feignClientInterface = cp.get(clientInterfaceClassType.getName());
        CtClass feignClientProxyAwareInterface = cp.get(FeignClientProxyAware.class.getName());
        CtClass feignResponseHandlerAwareInterface = cp.get(FeignResponseHandlerAware.class.getName());

        String decoratorClassPackage = clientInterfaceClassType.getPackage().getName() + ".decorator.";
        String feignClientDecoratorClass = decoratorClassPackage + clientInterfaceClassType.getSimpleName() + "Decorator0";

        CtClass decoratorCtClass = cp.makeClass(feignClientDecoratorClass);
        decoratorCtClass.setInterfaces(new CtClass[]{feignClientInterface, feignClientProxyAwareInterface, feignResponseHandlerAwareInterface});

        String feignClientProxyFN = "feignClientProxy";
        CtField feignClientProxyField = new CtField(feignClientInterface, feignClientProxyFN, decoratorCtClass);
        feignClientProxyField.setModifiers(Modifier.PRIVATE);
        decoratorCtClass.addField(feignClientProxyField);

        CtMethod setFeignClientProxyMethod = feignClientProxyAwareInterface.getDeclaredMethods()[0];
        CtMethod setFeignClientProxyMethodImp = CtNewMethod.copy(setFeignClientProxyMethod, setFeignClientProxyMethod.getName(), decoratorCtClass, null);
        setFeignClientProxyMethodImp.setBody("{this." + feignClientProxyFN + " = $1;}");
        decoratorCtClass.addMethod(setFeignClientProxyMethodImp);

        String feignResponseHandlerFN = "feignResponseHandler";
        CtField feignResponseHandlerField = new CtField(cp.get(FeignResponseHandler.class.getName()), feignResponseHandlerFN, decoratorCtClass);
        feignResponseHandlerField.setModifiers(Modifier.PRIVATE);
        decoratorCtClass.addField(feignResponseHandlerField);

        CtMethod setFeignResponseHandlerMethod = feignResponseHandlerAwareInterface.getDeclaredMethods()[0];
        CtMethod setFeignResponseHandlerMethodImp = CtNewMethod.copy(setFeignResponseHandlerMethod, setFeignResponseHandlerMethod.getName(), decoratorCtClass, null);
        setFeignResponseHandlerMethodImp.setBody("{this." + feignResponseHandlerFN + " = $1;}");
        decoratorCtClass.addMethod(setFeignResponseHandlerMethodImp);

        StringBuilder body;
        CtMethod[] feignClientMethods = feignClientInterface.getDeclaredMethods();
        for (CtMethod feignClientMethod : feignClientMethods) {

            CtMethod feignClientMethodImp = CtNewMethod.copy(feignClientMethod, feignClientMethod.getName(), decoratorCtClass, null);
            List srcAttrs = feignClientMethod.getMethodInfo().getAttributes();
            for (Object srcAttr : srcAttrs) {
                feignClientMethodImp.getMethodInfo().addAttribute((AttributeInfo) srcAttr);
            }

            body = new StringBuilder();
            body.append("{");
            body.append("Object res = this.").append(feignClientProxyFN).append(".").append(feignClientMethod.getName()).append("($$);");
            body.append("this.").append(feignResponseHandlerFN).append(".").append(FeignResponseHandler.HANDLE_METHOD_NAME).append("(res);");
            body.append("return res;");
            body.append("}");

            feignClientMethodImp.setBody(body.toString());
            decoratorCtClass.addMethod(feignClientMethodImp);
        }
        Class decoratorClassType = decoratorCtClass.toClass();
        decoratorCtClass.detach();
        return decoratorClassType;
    }
}