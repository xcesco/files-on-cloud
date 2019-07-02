package org.abubusoft.foc.business.services;

import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.Uploader;

public interface SendMailService {
    void sendError(Throwable e);

	void send(Uploader uploader, Consumer consumer, CloudFile file);
}
