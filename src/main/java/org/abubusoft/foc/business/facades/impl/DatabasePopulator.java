package org.abubusoft.foc.business.facades.impl;

import org.abubusoft.foc.business.facades.*;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.repositories.support.MimeTypeUtils;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabasePopulator implements Populator {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected AuthService authService;

    @Autowired
    public void setAuthService(AuthService identityManagementService) {
        this.authService = identityManagementService;
    }


    protected AdminFacade adminFacade;

    protected ConsumerFacade consumerFacade;

    protected UploaderFacade uploaderFacade;

    protected CloudFileFacade cloudFileFacade;

    @Autowired
    protected AuthService service;

    @Autowired
    protected CloudFileService cloudFileService;

    @Autowired
    public void setCloudFileFacade(CloudFileFacade cloudFileFacade) {
        this.cloudFileFacade = cloudFileFacade;
    }

    @Autowired
    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @Autowired
    public void setConsumerFacade(ConsumerFacade consumerFacade) {
        this.consumerFacade = consumerFacade;
    }

    @Autowired
    public void setUploaderFacade(UploaderFacade uploaderFacade) {
        this.uploaderFacade = uploaderFacade;
    }

    @Override
    public void execute() {
        // cancelliamo tutti i file ed utenti
        cloudFileService.deleteAllFiles();
        authService.deleteAllUsers();

        List<ConsumerWto> consumers = new ArrayList<>();
        List<UploaderWto> uploaders = new ArrayList<>();

        {
            // creazione admin
            AdminWto user = adminFacade.create();
            user.setDisplayName("Admin 0");
            user.setUsername("admin0@gmail.com");
            user.setEmail("uxcesco@gmail.com");
            user.setPassword("password");
            adminFacade.save(user);
        }

        // creiamo gli uploader ed i consumer
        for (int i = 0; i < 4; i++) {
            try {
                uploaders.add(createUploader(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 4; i++) {
            consumers.add(createConsumer(i));
        }

        {
            File image = new File("src/test/resources/images/wallhaven0.jpg");
            try {
                cloudFileFacade.save(uploaders.get(1).getId(), LocalDateTime.now(), consumers.get(1),
                        "wallpaper0", image.getName(),
                        MimeTypeUtils.getFromFileName(image.getName()), image.length(),
                        new FileInputStream(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        // creiamo gli altri file
        {
            long f = 1;
            long imageCounter = -1;

            for (int u = 2; u < uploaders.size(); u++) {
                for (int c = 2; c < consumers.size(); c++) {
                    for (int i = 0; i < f; i++) {
                        imageCounter = (imageCounter + 1) % 3;
                        File image = new File("src/test/resources/images/wallhaven" + imageCounter + ".jpg");
                        try {
                            cloudFileFacade.save(uploaders.get(u).getId(), LocalDateTime.now(), consumers.get(c),
                                    "wallpaper, image"+imageCounter+", nice", image.getName(),
                                    MimeTypeUtils.getFromFileName(image.getName()), image.length(),
                                    new FileInputStream(image));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                f++;
            }
        }
    }

    private ConsumerWto createConsumer(int index) {
        // creazione consumer
        ConsumerWto user = consumerFacade.create();
        user.setDisplayName("Consumer " + index);
        user.setUsername("consumer" + index + "@gmail.com");
        user.setEmail("uxcesco@gmail.com");
        user.setPassword("password");
        user.setCodiceFiscale("NSSMRA80A01L424" + index);
        user = consumerFacade.save(user);
        return user;
    }

    private UploaderWto createUploader(int index) throws FileNotFoundException {
        // creazione uploader
        UploaderWto user = uploaderFacade.create();
        user.setDisplayName("Uploader " + index);
        user.setUsername("uploader" + index + "@gmail.com");
        user.setEmail("uxcesco@gmail.com");
        user.setPassword("password");
        user = uploaderFacade.save(user);

        File image = new File("src/test/resources/avatars/avatar"+index+".png");
        uploaderFacade.saveLogo(user.getId(), new FileInputStream(image));

        return user;

    }
}
