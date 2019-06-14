package org.abubusoft.foc.services;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.abubusoft.foc.model.Consumer;

public interface CloudFileService {

	String uploadFile(Part filePart) throws IOException;

	String getImageUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

	String uploadFile(String filename, byte[] content) throws IOException;

	void uploadFile(String uploaderUsername, Consumer consumer, String fileName, byte[] content, Set<String> tags);

}
