package dev.lmarchesoti.ClienteSimulacao.util;

import dev.lmarchesoti.ClienteSimulacao.dto.SimulacaoDto;

import java.util.List;
import java.util.Objects;

public class TxtUtil {
    public static String convertToTxt(List<SimulacaoDto> content) {
        StringBuilder stringBuilder = new StringBuilder();

        String[] header = {
                "id",
                "createdAt",
                "valorSolicitado",
                "valorGarantia",
                "qtdMeses",
                "txJurosMensal"
        };
        stringBuilder.append(String.join("\t", header) + "\n");

        for (SimulacaoDto dto : content) {
            stringBuilder
                    .append(Objects.toString(dto.id(), ""))
                    .append("\t")
                    .append(Objects.toString(dto.createdAt(), ""))
                    .append("\t")
                    .append(Objects.toString(dto.valorSolicitado(), ""))
                    .append("\t")
                    .append(Objects.toString(dto.valorGarantia(), ""))
                    .append("\t")
                    .append(Objects.toString(dto.qtdMeses(), ""))
                    .append("\t")
                    .append(Objects.toString(dto.txJurosMensal(), ""))
                    .append("\n");
        }

        return stringBuilder.toString();
    }
}
