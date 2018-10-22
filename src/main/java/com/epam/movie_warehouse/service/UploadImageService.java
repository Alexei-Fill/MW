package com.epam.movie_warehouse.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class UploadImageService implements Service {
    private static final String PROJECT_FOLDER = "projectFolder";
    private static final String FILE_REQUEST_PART = "file";
    private static final String TEMP_FILE_PREFIX = "img-";
    private static final String TEMP_FILE_SUFFIX = ".jpg";
    private static final String PROJECT_UPLOAD_FOLDER = "/src/main/webapp";
    private static final String DEFAULT_UPLOAD_FOLDER = "/uploads_img/";
    private static final String USER_IMG_UPLOAD_FOLDER = "/uploads_img/user_img/";
    private static final String MOVIE_IMG_UPLOAD_FOLDER = "/uploads_img/movie_img/";
    private static final String HUMAN_IMG_UPLOAD_FOLDER = "/uploads_img/human_img/";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectFolder = request.getServletContext().getInitParameter(PROJECT_FOLDER);
        String requestURI = request.getRequestURI();
        Part filePart = request.getPart(FILE_REQUEST_PART);
        File uploads = new File(projectFolder + PROJECT_UPLOAD_FOLDER + getUploadFolderByURI(requestURI));
        File file = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX, uploads);
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        String filePath = getUploadFolderByURI(requestURI) + file.getName();
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        request.setAttribute(IMG_URL, filePath);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestURL);
        requestDispatcher.forward(request, response);
    }

    private String getUploadFolderByURI(String requestURI) {
        String uploadFolder;
        switch (requestURI) {
            case UPLOAD_USER_IMAGE_URI: {
                uploadFolder = USER_IMG_UPLOAD_FOLDER;
                break;
            }
            case UPLOAD_MOVIE_IMAGE_URI: {
                uploadFolder = MOVIE_IMG_UPLOAD_FOLDER;
                break;
            }
            case UPLOAD_HUMAN_IMAGE_URI: {
                uploadFolder = HUMAN_IMG_UPLOAD_FOLDER;
                break;
            }
            default: {
                uploadFolder = DEFAULT_UPLOAD_FOLDER;
                break;
            }
        }
        return uploadFolder;
    }
}
