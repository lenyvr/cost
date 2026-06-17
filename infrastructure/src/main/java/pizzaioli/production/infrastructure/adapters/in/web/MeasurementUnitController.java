package pizzaioli.production.infrastructure.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.MeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.infrastructure.dtos.request.CreateMeasurementUnitRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.MeasurementUnitResponseDTO;
import pizzaioli.production.infrastructure.mapper.MeasurementUnitMapper;

@RestController
@RequestMapping("/api/v1/measurement-units")
public class MeasurementUnitController {

    private final MeasurementUnitUseCaseSPI measurementUnitUseCaseSPI;

    public MeasurementUnitController(MeasurementUnitUseCaseSPI measurementUnitUseCaseSPI) {
        this.measurementUnitUseCaseSPI = measurementUnitUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementUnitResponseDTO create(@Valid @RequestBody CreateMeasurementUnitRequestDTO request) {
       MeasurementUnit measurementUnitSaved = measurementUnitUseCaseSPI
                .create(MeasurementUnitMapper.toDomainFromCreateRequest(request));
         return MeasurementUnitMapper.toCreationResponseDTO(measurementUnitSaved);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        measurementUnitUseCaseSPI.delete(code);
    }
}
