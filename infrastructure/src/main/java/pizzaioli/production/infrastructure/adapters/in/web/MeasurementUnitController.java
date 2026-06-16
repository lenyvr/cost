package pizzaioli.production.infrastructure.adapters.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.infrastructure.dtos.request.CreateMeasurementUnitRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.MeasurementUnitResponseDTO;
import pizzaioli.production.infrastructure.mapper.MeasurementUnitMapper;

@RestController
@RequestMapping("/api/v1/measurement-units")
public class MeasurementUnitController {

    private final CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCaseSPI;
    private final DeleteMeasurementUnitUseCaseSPI deleteMeasurementUnitUseCaseSPI;

    public MeasurementUnitController(CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCaseSPI,
                                     DeleteMeasurementUnitUseCaseSPI deleteMeasurementUnitUseCaseSPI) {
        this.createMeasurementUnitUseCaseSPI = createMeasurementUnitUseCaseSPI;
        this.deleteMeasurementUnitUseCaseSPI = deleteMeasurementUnitUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementUnitResponseDTO create(@RequestBody CreateMeasurementUnitRequestDTO request) {
       MeasurementUnit measurementUnitSaved = createMeasurementUnitUseCaseSPI
                .execute(MeasurementUnitMapper.toDomainFromCreateRequest(request));
         return MeasurementUnitMapper.toCreationResponseDTO(measurementUnitSaved);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        deleteMeasurementUnitUseCaseSPI.execute(code);
    }
}
