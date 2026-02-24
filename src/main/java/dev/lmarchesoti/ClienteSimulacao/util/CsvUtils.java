package dev.lmarchesoti.ClienteSimulacao.util;

import com.opencsv.CSVWriter;
import dev.lmarchesoti.ClienteSimulacao.dto.SimulacaoDto;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

public class CsvUtils {

    public static String convertToCsv(List<SimulacaoDto> content) {
        StringWriter writer = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {

            String[] header = {
                    "id",
                    "createdAt",
                    "valorSolicitado",
                    "valorGarantia",
                    "qtdMeses",
                    "txJurosMensal"
            };
            csvWriter.writeNext(header);

            for (SimulacaoDto dto : content) {
                String[] row = {
                        Objects.toString(dto.id(), ""),
                        Objects.toString(dto.createdAt(), ""),
                        Objects.toString(dto.valorSolicitado(), ""),
                        Objects.toString(dto.valorGarantia(), ""),
                        Objects.toString(dto.qtdMeses(), ""),
                        Objects.toString(dto.txJurosMensal(), "")
                };
                csvWriter.writeNext(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return writer.toString();
    }
}
