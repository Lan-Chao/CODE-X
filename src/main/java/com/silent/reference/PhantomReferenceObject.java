package com.silent.reference;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Cleaner;

/**
 * @author zhao
 * @date 2021/11/12 13:58
 */

@Data
@Slf4j
public class PhantomReferenceObject {

    private String name;

    private Cleaner cleaner;


    public PhantomReferenceObject() {
        cleaner = Cleaner.create(this, () -> {
            log.info("===== 清理 对象 =====");
        });
    }


}
