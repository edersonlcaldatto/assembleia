package com.dbc.assembleia.transportlayer.data.response;

import com.dbc.assembleia.entity.enumerator.StatusEnum;

public record SessaoResponse(Integer id, PautaResponse pauta, StatusEnum status) {
}
