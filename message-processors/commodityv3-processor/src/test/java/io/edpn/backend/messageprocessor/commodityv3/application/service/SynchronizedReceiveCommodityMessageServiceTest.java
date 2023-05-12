package io.edpn.backend.messageprocessor.commodityv3.application.service;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.*;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.*;
import io.edpn.backend.messageprocessor.domain.util.TimestampConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SynchronizedReceiveCommodityMessageServiceTest {

    private SynchronizedReceiveCommodityMessageService messageService;
    @Mock
    private SystemRepository systemRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private CommodityRepository commodityRepository;
    @Mock
    private EconomyRepository economyRepository;
    @Mock
    private HistoricStationCommodityMarketDatumRepository historicStationCommodityMarketDatumRepository;
    @Mock
    private StationProhibitedCommodityRepository stationProhibitedCommodityRepository;
    @Mock
    private StationEconomyProportionRepository stationEconomyProportionRepository;

    @BeforeEach
    void setUp() {
        messageService = new SynchronizedReceiveCommodityMessageService(
                systemRepository,
                stationRepository,
                commodityRepository,
                economyRepository,
                historicStationCommodityMarketDatumRepository,
                stationProhibitedCommodityRepository,
                stationEconomyProportionRepository
        );
    }

    @Test
    void testReceiveCommodityMessage() {
        // Prepare test data
        String stationName = "Olivas Terminal";
        String systemName = "BD+65 1846";
        long marketId = 3228085760L;
        String messageTimestamp = "2023-05-10T14:44:30Z";
        LocalDateTime messageLocalDateTimeStamp = TimestampConverter.convertToLocalDateTime(messageTimestamp);

        CommodityMessage.V3 commodityMessage = new CommodityMessage.V3();
        CommodityMessage.V3.Economy economy = new CommodityMessage.V3.Economy();
        economy.setName("Carrier");
        economy.setProportion(1);

        // Set the necessary properties in the commodityMessage object
        CommodityMessage.V3.Message message = new CommodityMessage.V3.Message();
        message.setTimestamp(messageTimestamp);
        message.setSystemName(systemName);
        message.setStationName(stationName);
        message.setMarketId(marketId);
        message.setProhibited(new String[]{"ImperialSlaves", "Slaves"});
        message.setEconomies(new CommodityMessage.V3.Economy[]{economy});

        CommodityMessage.V3.Commodity commodity1 = new CommodityMessage.V3.Commodity();
        commodity1.setBuyPrice(15636);
        commodity1.setDemand(0);
        commodity1.setDemandBracket(0);
        commodity1.setMeanPrice(21542);
        commodity1.setName("agriculturalmedicines");
        commodity1.setSellPrice(15481);
        commodity1.setStock(0);
        commodity1.setStockBracket(0);

        CommodityMessage.V3.Commodity commodity2 = new CommodityMessage.V3.Commodity();
        commodity2.setBuyPrice(0);
        commodity2.setDemand(1114);
        commodity2.setDemandBracket(3);
        commodity2.setMeanPrice(3105);
        commodity2.setName("alacarakmoskinart");
        commodity2.setSellPrice(8899);
        commodity2.setStock(0);
        commodity2.setStockBracket(0);

        message.setCommodities(new CommodityMessage.V3.Commodity[]{commodity1, commodity2});

        commodityMessage.setMessage(message);

        // setup entities
        UUID systemEntityId = UUID.randomUUID();
        SystemEntity systemEntity = SystemEntity.builder()
                .id(systemEntityId)
                .name(systemName)
                .build();

        UUID stationEntityId = UUID.randomUUID();
        StationEntity stationEntity = new StationEntity();
        stationEntity.setId(stationEntityId);
        stationEntity.setName(stationName);
        stationEntity.setSystemId(systemEntityId);

        UUID prohibitedCommodityEntityId1 = UUID.randomUUID();
        CommodityEntity prohibitedCommodityEntity1 = new CommodityEntity();
        prohibitedCommodityEntity1.setId(prohibitedCommodityEntityId1);
        prohibitedCommodityEntity1.setName("agriculturalmedicines");

        UUID prohibitedCommodityEntityId2 = UUID.randomUUID();
        CommodityEntity prohibitedCommodityEntity2 = new CommodityEntity();
        prohibitedCommodityEntity2.setId(prohibitedCommodityEntityId2);
        prohibitedCommodityEntity2.setName("alacarakmoskinart");

        UUID commodityEntityId1 = UUID.randomUUID();
        CommodityEntity commodityEntity1 = new CommodityEntity();
        commodityEntity1.setId(commodityEntityId1);
        commodityEntity1.setName("agriculturalmedicines");

        UUID commodityEntityId2 = UUID.randomUUID();
        CommodityEntity commodityEntity2 = new CommodityEntity();
        commodityEntity2.setId(commodityEntityId2);
        commodityEntity2.setName("alacarakmoskinart");

        UUID economyEntityId = UUID.randomUUID();
        EconomyEntity economy1 = new EconomyEntity(economyEntityId, "Carrier");

        // Mock repository methods
        when(systemRepository.findOrCreateByName(systemName)).thenReturn(systemEntity);

        when(stationRepository.findByMarketId(anyLong())).thenReturn(Optional.empty());

        when(stationRepository.findOrCreateBySystemIdAndStationName(systemEntityId, stationName)).thenReturn(stationEntity);

        when(commodityRepository.findOrCreateByName("ImperialSlaves")).thenReturn(prohibitedCommodityEntity1);
        when(commodityRepository.findOrCreateByName("Slaves")).thenReturn(prohibitedCommodityEntity2);
        when(commodityRepository.findOrCreateByName("agriculturalmedicines")).thenReturn(commodityEntity1);
        when(commodityRepository.findOrCreateByName("alacarakmoskinart")).thenReturn(commodityEntity2);

        when(economyRepository.findOrCreateByName("Carrier")).thenReturn(economy1);

        when(historicStationCommodityMarketDatumRepository.getByStationIdAndCommodityIdAndTimestamp(
                stationEntityId, commodityEntityId1, messageLocalDateTimeStamp)).thenReturn(Optional.empty());

        // Call the method to test
        messageService.receive(commodityMessage);

        // Verify that the repository methods were called with the expected arguments
        verify(stationProhibitedCommodityRepository, times(1)).deleteByStationId(stationEntityId);
        verify(stationProhibitedCommodityRepository, times(1)).insert(stationEntityId, List.of(prohibitedCommodityEntityId1, prohibitedCommodityEntityId2));
        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(stationRepository, times(1)).findByMarketId(anyLong());
        verify(stationRepository, times(1)).findOrCreateBySystemIdAndStationName(org.mockito.ArgumentMatchers.any(UUID.class), anyString());
        verify(commodityRepository, times(1)).findOrCreateByName("ImperialSlaves");
        verify(commodityRepository, times(1)).findOrCreateByName("Slaves");
        verify(stationProhibitedCommodityRepository, times(1)).deleteByStationId(org.mockito.ArgumentMatchers.any(UUID.class));
        verify(stationProhibitedCommodityRepository, times(1)).insert(org.mockito.ArgumentMatchers.any(UUID.class), anyList());
        verify(stationEconomyProportionRepository, times(1)).insert(org.mockito.ArgumentMatchers.any());

        verify(economyRepository, times(1)).findOrCreateByName("Carrier");
        verify(stationEconomyProportionRepository, times(1)).insert(
                argThat(itemList -> {
                            for (StationEconomyProportionEntity item : itemList) {
                                if (!stationEntityId.equals(item.getStationId())) {
                                    return false;
                                }
                                if (!economyEntityId.equals(item.getEconomyId())) {
                                    return false;
                                }
                                if (1.0 != item.getProportion()) {
                                    return false;
                                }
                            }
                            return true;
                        }
                ));

        verify(commodityRepository, times(1)).findOrCreateByName("alacarakmoskinart");
        verify(commodityRepository, times(1)).findOrCreateByName("agriculturalmedicines");
        verify(historicStationCommodityMarketDatumRepository, times(1)).getByStationIdAndCommodityIdAndTimestamp(
                org.mockito.ArgumentMatchers.any(UUID.class), eq(commodityEntityId1), org.mockito.ArgumentMatchers.any(LocalDateTime.class));
        verify(historicStationCommodityMarketDatumRepository, times(1)).getByStationIdAndCommodityIdAndTimestamp(
                org.mockito.ArgumentMatchers.any(UUID.class), eq(commodityEntityId2), org.mockito.ArgumentMatchers.any(LocalDateTime.class));

        // Verify that the repository methods were called in the correct order
        InOrder inOrder = inOrder(systemRepository, stationRepository, stationProhibitedCommodityRepository, commodityRepository, historicStationCommodityMarketDatumRepository, economyRepository, stationEconomyProportionRepository);
        inOrder.verify(systemRepository).findOrCreateByName(anyString());

        inOrder.verify(stationRepository).findByMarketId(anyLong());
        inOrder.verify(stationRepository).findOrCreateBySystemIdAndStationName(org.mockito.ArgumentMatchers.any(UUID.class), anyString());
        inOrder.verify(stationRepository).update(any());

        inOrder.verify(commodityRepository, times(2)).findOrCreateByName(argThat(
                name -> name.equals("ImperialSlaves") || name.equals("Slaves")
        ));
        inOrder.verify(stationProhibitedCommodityRepository, times(1)).deleteByStationId(any());
        inOrder.verify(stationProhibitedCommodityRepository, times(1)).insert(any(), anyList());

        inOrder.verify(economyRepository, times(1)).findOrCreateByName(anyString());
        inOrder.verify(stationEconomyProportionRepository, times(1)).deleteByStationId(any());
        inOrder.verify(stationEconomyProportionRepository, times(1)).insert(any());

        inOrder.verify(historicStationCommodityMarketDatumRepository).getByStationIdAndCommodityIdAndTimestamp(
                org.mockito.ArgumentMatchers.any(UUID.class), org.mockito.ArgumentMatchers.any(UUID.class), org.mockito.ArgumentMatchers.any(LocalDateTime.class));
        // Capture the arguments passed to create() method
        ArgumentCaptor<HistoricStationCommodityMarketDatumEntity> argumentCaptor = ArgumentCaptor.forClass(HistoricStationCommodityMarketDatumEntity.class);
        verify(historicStationCommodityMarketDatumRepository, times(2)).create(argumentCaptor.capture());


        // Assert the captured argument values
        HistoricStationCommodityMarketDatumEntity capturedDatum1 = argumentCaptor.getAllValues().stream()
                .filter(c -> c.getCommodityId().equals(commodityEntityId1))
                .findFirst()
                .orElse(null);
        assertThat(capturedDatum1, notNullValue());
        assertThat(capturedDatum1.getStationId(), is(stationEntityId));
        assertThat(capturedDatum1.getCommodityId(), is(commodityEntityId1));
        assertThat(capturedDatum1.getTimestamp(), is(messageLocalDateTimeStamp));
        assertThat(capturedDatum1.getBuyPrice(), is(15636));
        assertThat(capturedDatum1.getDemand(), is(0));
        assertThat(capturedDatum1.getDemandBracket(), is(0));
        assertThat(capturedDatum1.getMeanPrice(), is(21542));
        assertThat(capturedDatum1.getSellPrice(), is(15481));
        assertThat(capturedDatum1.getStock(), is(0));
        assertThat(capturedDatum1.getStockBracket(), is(0));

        HistoricStationCommodityMarketDatumEntity capturedDatum2 = argumentCaptor.getAllValues().stream()
                .filter(c -> c.getCommodityId().equals(commodityEntityId2))
                .findFirst()
                .orElse(null);
        assertThat(capturedDatum2, notNullValue());
        assertThat(capturedDatum2.getStationId(), is(stationEntityId));
        assertThat(capturedDatum2.getCommodityId(), is(commodityEntityId2));
        assertThat(capturedDatum2.getTimestamp(), is(messageLocalDateTimeStamp));
        assertThat(capturedDatum2.getBuyPrice(), is(0));
        assertThat(capturedDatum2.getDemand(), is(1114));
        assertThat(capturedDatum2.getDemandBracket(), is(3));
        assertThat(capturedDatum2.getMeanPrice(), is(3105));
        assertThat(capturedDatum2.getSellPrice(), is(8899));
        assertThat(capturedDatum2.getStock(), is(0));
        assertThat(capturedDatum2.getStockBracket(), is(0));
    }
}