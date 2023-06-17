sequenceDiagram
Kafka->>+commodityFinder:approachsettlement
commodityFinder->>+commodityData:coalesce(MarketId, BodyId, coords, SystemAddress, StarSystem)
commodityData->>-commodityFinder:ack
commodityFinder->>-Kafka:commodityUpdated

Kafka->>+commodityFinder:commodity
commodityFinder->>+commodityData:coalesce(commodities(prices etc), marketId, stationName, systemName)
commodityData->>-commodityFinder:ack
commodityFinder->>-Kafka:commodityUpdated

Kafka->>+commodityFinder:fcmaterials_capi
commodityFinder->>+commodityData:coalesce(Items.purchases,Items.sales)
commodityData->>-commodityFinder:ack
commodityFinder->>-Kafka:commodityUpdated

# notes:
    #   ApproachSettlement has: BodyId, BodyName, Lat, Long, MarketId, Name, StarPos, SystemName, SystemAddress
#   CodexEntry: has body/system data, but says nothing about Commodity, so can likely be ignored by commodityFinder
#   Commodity: gives prohibited commodities, but not related to "best prices" feature
#   FcMaterials_Capi: includes "outstanding" potentially indicating the number of commodity at that price
#   FcMaterials_journal: seems useless for bestPrices
    #   FssBodiesFound: seems useless for bestPrices
    #   FssBodySignals: seems useless for bestPrices
    #   FssDiscoveryScan: seems useless for bestPrices
    #   Journal: depending on type, has StationServices[] that includes "commodities" (seen when event=Location && docked=true) - could potentially coalesce
#   NavBeaconScan: seems useless for bestPrices
    #   NavRoute: seems useless for bestPrices
    #   Outfitting: seems useless for bestPrices
    #   ScanBaryCentre: seems useless for bestPrices
