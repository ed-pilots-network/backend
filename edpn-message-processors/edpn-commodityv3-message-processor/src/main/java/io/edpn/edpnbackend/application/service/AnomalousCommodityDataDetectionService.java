package io.edpn.edpnbackend.application.service;

import io.edpn.edpnbackend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.edpnbackend.application.usecase.AnomalousCommodityDataDetectionUseCase;
import io.edpn.edpnbackend.domain.repository.HistoricStationCommodityMarketDatumRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.util.FastMath;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class AnomalousCommodityDataDetectionService implements AnomalousCommodityDataDetectionUseCase {

    private final HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository;
    @Value("${commodity.anomaly.threshold:2.5}")
    private final double anomalyThreshold;
    @Value("${commodity.anomaly.timeframe:6}")
    private final int monthsLookBack;

    private final double meanPriceWeight = 1.0;
    private final double buyPriceWeight = 1.0;
    private final double stockWeight = 1.0;
    private final double sellPriceWeight = 1.0;
    private final double demandWeight = 1.0;

    @Override
    public boolean isAnomalous(HistoricStationCommodityMarketDatumEntity commodityMarketDatumEntity) {
        // Calculate the means and covariance matrix for the data
        double[] mean = {commodityMarketDatumEntity.getMeanPrice(), commodityMarketDatumEntity.getBuyPrice(), commodityMarketDatumEntity.getSellPrice(), commodityMarketDatumEntity.getDemand()};
        double[][] cov = calculateCovarianceMatrix(commodityMarketDatumEntity.getCommodityId());

        // Calculate the Mahalanobis Distance of the latest data point from the mean
        double[] data = {commodityMarketDatumEntity.getMeanPrice(), commodityMarketDatumEntity.getBuyPrice(), commodityMarketDatumEntity.getSellPrice(), commodityMarketDatumEntity.getDemand(), commodityMarketDatumEntity.getStock()};
        double distance = mahalanobisDistance(data, mean, cov);

        // If the Mahalanobis Distance is greater than the threshold, flag the data point as anomalous
        return distance > anomalyThreshold;
    }

    private double[][] calculateCovarianceMatrix(UUID commodityId) {
        // Calculate the start and end timestamps for the latest 6 months
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(monthsLookBack);

        // Retrieve the data points for this commodity within the latest 6 months from the repository
        Collection<HistoricStationCommodityMarketDatumEntity> data = historicStationCommodityMarketDatumRepository.findByCommodityIdAndTimestampBetween(commodityId, start, end);

        // Calculate the covariance matrix of the data
        double[][] matrix;
        double[] mean = new double[5];
        for (HistoricStationCommodityMarketDatumEntity d : data) {
            mean[0] += d.getMeanPrice();
            mean[1] += d.getBuyPrice();
            mean[2] += d.getSellPrice();
            mean[3] += d.getDemand();
            mean[4] += d.getStock();
        }
        for (int i = 0; i < mean.length; i++) {
            mean[i] /= data.size();
        }
        Covariance cov = new Covariance(data.stream()
                .map(d -> new double[]{d.getMeanPrice() - mean[0], d.getBuyPrice() - mean[1], d.getSellPrice() - mean[2], d.getDemand() - mean[3], d.getStock() - mean[4]})
                .toArray(double[][]::new));
        matrix = cov.getCovarianceMatrix().getData();
        return matrix;
    }


    private double mahalanobisDistance(double[] data, double[] mean, double[][] cov) {
        double[] diff = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            diff[i] = data[i] - mean[i];
        }
        double[][] invCov = new LUDecomposition(new Array2DRowRealMatrix(cov)).getSolver().getInverse().getData();
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            double temp = 0;
            for (int j = 0; j < data.length; j++) {
                temp += diff[j] * invCov[j][i];
            }
            sum += temp * diff[i];
        }
        return FastMath.sqrt(sum);
    }

}
