package util;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.Part;

public class FichierValidateur {
	private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 Mo
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "pdf"};

    public static boolean isFileValid(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) {
            return false;
        }

        //   la taille du fichier
        if (filePart.getSize() > MAX_SIZE) {
            System.out.println("Le fichier est trop grand. La taille maximale est de 5 Mo.");
            return false;
        }

        // l'extension du fichier
        String fileName = filePart.getSubmittedFileName();
        if (fileName != null) {
            String extension = getFileExtension(fileName).toLowerCase();

            //  l'extension est valide ?
            boolean isValidExtension = false;
            for (String ext : ALLOWED_EXTENSIONS) {
                if (extension.equals(ext)) {
                    isValidExtension = true;
                    break;
                }
            }

            if (!isValidExtension) {
                System.out.println("Extension de fichier non autorisée. Seuls les fichiers .jpg, .jpeg, .png, .pdf sont acceptés.");
                return false;
            }

            // verifier si le fichier n'est pas un .exe
            if (extension.equals("exe")) {
                System.out.println("Le fichier .exe n'est pas autorisé.");
                return false;
            }
        }

        return true;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    //  (lecture en bytes)
    public static byte[] processFile(Part filePart) throws IOException {
        byte[] fileBytes = null;
        try (InputStream inputStream = filePart.getInputStream()) {
            fileBytes = inputStream.readAllBytes();
        }
        return fileBytes;
    }
}
