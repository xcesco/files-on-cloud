package org.abubusoft.foc.services;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.util.MimeType;

public interface CloudFileService {

	String uploadFile(Part filePart) throws IOException;

	String getImageUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

	String uploadFile(String filename, InputStream inputStream) throws IOException;

}
