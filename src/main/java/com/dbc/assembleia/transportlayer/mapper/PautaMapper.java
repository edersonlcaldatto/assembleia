package com.dbc.assembleia.transportlayer.mapper;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.transportlayer.data.request.PautaRequest;
import com.dbc.assembleia.transportlayer.data.response.PautaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);
    Pauta fromPautaRequest(PautaRequest source);
    PautaResponse toPautaResponse(Pauta pauta);
}
