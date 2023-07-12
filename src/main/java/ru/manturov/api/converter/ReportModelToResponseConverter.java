package ru.manturov.api.converter;

import org.springframework.stereotype.Component;
import ru.manturov.api.json.ReportResponse;
import ru.manturov.dao.ReportModel;

@Component
public class ReportModelToResponseConverter implements Converter<ReportModel, ReportResponse> {

    @Override
    public ReportResponse convert(ReportModel reportModel) {
        return new ReportResponse(reportModel.getCategoryName(), reportModel.getValue());
    }
}
