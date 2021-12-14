package com.silent.base.loader;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 * @author zhao
 * @date 2021/11/29 17:44
 */

@Slf4j
public class JavaClassExecuter {



    /**
     * 执行外部传过来的代表一个Java类的Byte数组<br>
     * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类的static main(String[] args)方法，输出结果为该类向System.out/err输出的信息
     * @param classByte 代表一个Java类的Byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com.silent.base.loader.HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("print", String.class);
            method.invoke(null, "hhhhh");
        } catch (Throwable e) {
            log.error("out:{}",HackSystem.out, e);
        }
        return HackSystem.getBufferString();
    }

}
