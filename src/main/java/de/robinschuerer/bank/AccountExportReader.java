package de.robinschuerer.bank;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.robinschuerer.bank.dto.AccountMovementDto;

public interface AccountExportReader {

    List<AccountMovementDto> importData(File file, final UUID ticket, final ConcurrentHashMap<UUID, Integer> status);

    String getType();

}

