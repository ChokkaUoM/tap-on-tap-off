package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TapDataRepository implements AbstractDataRepository<TapDataInput> {

    private static final Map<String, TapDataInput> mapById = new HashMap<>();

    private static final Map<String, List<TapDataInput>> mapByPAN = new HashMap<>();

    private static final Map<String, List<TapDataInput>> mapByBusIDAndPAN = new HashMap<>();

    @Override
    public void save(TapDataInput data) {
        //Assume we don't have duplicate data -> id won't be duplicated
        mapById.putIfAbsent(Optional.ofNullable(data.getId())
                .orElseThrow(() -> new InvalidDataException("Tap ID is missing")), data);
        mapByPAN.computeIfAbsent(Optional.ofNullable(data.getPan())
                .orElseThrow(() -> new InvalidDataException("PAN is missing")), k -> new ArrayList<>());
        mapByPAN.get(data.getPan()).add(data);

        if (!StringUtils.hasText(data.getBusId()) || !StringUtils.hasText(data.getPan())) {
            throw new InvalidDataException("BusID or PAN is missing");
        }

        String compositeKey = String.format("%s:%s", data.getBusId(), data.getPan());
        mapByBusIDAndPAN.computeIfAbsent(compositeKey, k -> new ArrayList<>());
        mapByBusIDAndPAN.get(compositeKey).add(data);
    }

    @Override
    public List<TapDataInput> findAll() {
        return mapById.values().stream().collect(Collectors.toList());
    }

    @Override
    public TapDataInput findById(String id) {
        return mapById.get(id);
    }

    public List<TapDataInput> findByPan(String pan) {
        return mapByPAN.get(pan);
    }

    public Map<String, List<TapDataInput>> getMapByPAN() {
        return mapByPAN;
    }

    public Map<String, List<TapDataInput>> getMapByBusIDAndPAN() {
        return mapByBusIDAndPAN;
    }
}
