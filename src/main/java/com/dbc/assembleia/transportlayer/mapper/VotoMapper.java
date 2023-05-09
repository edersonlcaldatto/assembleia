package com.dbc.assembleia.transportlayer.mapper;

import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    @Mapping(source = "codigoSessao", target = "sessao", qualifiedByName = "codigoSessaoToSessa")
    Voto fromVotoRequest(VotoRequest source);

    @Named("codigoSessaoToSessa")
    public static Sessao codigoSessaoToSessa(Integer codigoPauta) {
        return new Sessao(codigoPauta);
    }
}
