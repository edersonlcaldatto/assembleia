package com.dbc.assembleia.transportlayer.mapper;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.transportlayer.data.request.SessaoRequest;
import com.dbc.assembleia.transportlayer.data.response.SessaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SessaoMapper {

    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);

    @Mapping(source = "codigoPauta", target = "pauta", qualifiedByName = "codigoToPauta")
    Sessao fromSesssaoRequest(SessaoRequest source);
    SessaoResponse toSessaoResponse(Sessao sessao);

    @Named("codigoToPauta")
    static Pauta codigoToPauta(Integer codigoPauta) {
        var pauta = new Pauta();
        pauta.setId(codigoPauta);
        return pauta;
    }
}
