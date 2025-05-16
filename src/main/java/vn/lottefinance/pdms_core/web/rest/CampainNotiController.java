package vn.lottefinance.pdms_core.web.rest;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDTO;
import vn.lottefinance.pdms_core.service.core.CampainNotiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/campainNoti")
public class CampainNotiController {

    private final CampainNotiService service;

    public CampainNotiController(CampainNotiService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CampainNotiDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampainNotiDTO.Response> getById(@PathVariable Long id) {
        CampainNotiDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CampainNotiDTO.Response> create(@RequestBody CampainNotiDTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampainNotiDTO.Response> update(@PathVariable Long id, @RequestBody CampainNotiDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponseDTO<CampainNotiDTO.UploadFileResponse>> uploadCampain(@RequestParam(value = "file") MultipartFile file,
                                                                                     @RequestParam("fileName") String fileName) {

        return ResponseEntity.ok(BaseResponseDTO.<CampainNotiDTO.UploadFileResponse>builder()
                .data(service.processExtractCampainUpload(file, fileName))
                .build());
    }

    @PostMapping("/addNew")
    public ResponseEntity<BaseResponseDTO> addNewCampain(@RequestBody @Valid CampainNotiDTO.AddRequest request) {
        return ResponseEntity.ok(service.addNewCampain(request));
    }

}
