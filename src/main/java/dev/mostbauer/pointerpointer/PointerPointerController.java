package dev.mostbauer.pointerpointer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PointerPointerController {

    @GetMapping("/getPictureXY")
    @CrossOrigin(origins = "http://localhost:63342") // Allow request from Frontend
    public ResponseEntity<byte[]> getPictureXY(@RequestParam(value = "xMouse") int xMouse,
                                               @RequestParam(value = "yMouse") int yMouse,
                                               @RequestParam(value = "screenWidth") int screenWidth,
                                               @RequestParam(value = "screenHeight") int screenHeight) {


        final int[] indices = getIndices(xMouse, yMouse, screenWidth, screenHeight, 6, 6);
        final int xIdx = indices[0];
        final int yIdx = indices[1];

        final String assetFolder = "/home/julian/IdeaProjects/PointerPointer/assets";
        final String filePath = String.format("%s/panel_%d_%d.jpg", assetFolder, xIdx, yIdx);

        try {
            Path imagePath = Paths.get(filePath);
            byte[] imageBytes = Files.readAllBytes(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);
            System.out.println("Returning image for x=" + xIdx + ", y=" + yIdx);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static int[] getIndices(int xMouse, int yMouse, int screenWidth, int screenHeight, int xPanels, int yPanels) {
        final int xIdx = (int) Math.floor((double) xMouse / screenWidth * xPanels);
        final int yIdx = (int) Math.floor((double) yMouse / screenHeight * yPanels);
        return new int[]{xIdx, yIdx};
    }

}
