package org.abubusoft.foc.business.facades;

import javax.transaction.Transactional;

public interface Populator {

    @Transactional
    void execute();
}
