package me.fortibrine.justreports.reputation;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@DatabaseTable(tableName = "player_reputation")
@NoArgsConstructor
public class PlayerReputationModel {
    @DatabaseField(columnName = "player_id", canBeNull = false)
    private UUID playerId;

    @DatabaseField(columnName = "reputation", canBeNull = false)
    private int reputation;
}
