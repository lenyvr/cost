package pizzaioli.production.infrastructure.adapters.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.infrastructure.dtos.request.CreateMeasurementUnitRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.MeasurementUnitResponseDTO;
import pizzaioli.production.infrastructure.mapper.MeasurementUnitMapper;

@RestController
@RequestMapping("/api/v1/measurement-units")
public class MeasurementUnitController {

    private final CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCaseSPI;

    public MeasurementUnitController(CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCaseSPI) {
        this.createMeasurementUnitUseCaseSPI = createMeasurementUnitUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementUnitResponseDTO create(@RequestBody CreateMeasurementUnitRequestDTO request) {
       MeasurementUnit measurementUnitSaved = createMeasurementUnitUseCaseSPI
                .execute(MeasurementUnitMapper.toDomainFromCreateRequest(request));
         return MeasurementUnitMapper.toCreationResponseDTO(measurementUnitSaved);
    }
}
