package dev.mostbauer.pointerpointer.catcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/catcher")
public class CatcherController {

    private final CatcherService catcherService;

    @Autowired
    public CatcherController(CatcherService catcherService) {
        this.catcherService = catcherService;
    }

    @GetMapping("/getPictureXY")
    public ResponseEntity<byte[]> getPictureXY(@RequestParam(value = "xMouse") int xMouse,
                                               @RequestParam(value = "yMouse") int yMouse,
                                               @RequestParam(value = "screenWidth") int screenWidth,
                                               @RequestParam(value = "screenHeight") int screenHeight) {
        return catcherService.getPictureXY(xMouse, yMouse, screenWidth, screenHeight);
    }
}
